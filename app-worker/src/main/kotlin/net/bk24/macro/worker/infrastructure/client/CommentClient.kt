package net.bk24.macro.worker.infrastructure.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.benmanes.caffeine.cache.Cache
import kotlinx.coroutines.reactive.awaitSingle
import net.bk24.macro.worker.infrastructure.client.WebClientHelper.Companion.createFormData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import java.time.Instant

@Component
class CommentClient(
    private val webClient: WebClient,
    private val webClientHelper: WebClientHelper,
    private val authCodeCache: Cache<String, Long>,
    private val objectMapper: ObjectMapper,
) {
    private val logger = LoggerFactory.getLogger("comment-client")

    suspend fun makeRequest(
        authCode: String,
        snsId: String,
        postNo: String,
    ): WriteResponse? {
        val now = Instant.now().toEpochMilli()
        val lastTime = authCodeCache.getIfPresent(authCode)

        // 최소 요청 간격 확인
        if (lastTime != null && (now - lastTime) < 6532) {
//            logger.info("Request too soon after last request. Skipping.")
            return null
        }

        // 캐시 업데이트
        authCodeCache.put(authCode, now)
        val formData = createFormData(authCode, snsId, postNo)

        return try {
            webClient.post()
                .uri("/Sns/PostCommentCreate.aspx")
                .headers { headers -> headers.addAll(webClientHelper.setupHeaders()) }
                .body(formData)
                .exchangeToMono { it.bodyToMono(String::class.java) }
                .map { objectMapper.readValue(it, WriteResponse::class.java) }
                .awaitSingle()
        } catch (e: Exception) {
            logger.error("Exception caught during web request: ${e.message}", e)
            null // 이 부분에서 null을 허용하는 것은 타입 세이프티를 위반하지 않으며, Kotlin의 null 처리 규칙에 따릅니다.
        }
    }
}
