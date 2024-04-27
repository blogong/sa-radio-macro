package net.bk24.macro.worker.infrastructure.webclient

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.benmanes.caffeine.cache.Cache
import kotlinx.coroutines.reactive.awaitSingle
import net.bk24.macro.worker.infrastructure.webclient.WebClientHelper.Companion.createFormData
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.util.retry.Retry
import java.time.Duration
import java.time.Instant

@Component
class CommentClient(
    private val webClient: WebClient,
    private val webClientHelper: WebClientHelper,
    private val authCodeCache: Cache<String, Long>,
    private val objectMapper: ObjectMapper,
) {

    suspend fun makeRequest(
        authCode: String,
        snsId: String,
        postNo: String,
    ): WriteResponse? {
        val now = Instant.now().toEpochMilli()
        val lastTime = authCodeCache.getIfPresent(authCode)

        if (lastTime != null && (now - lastTime) < 6212) {
            return null
        }

        authCodeCache.put(authCode, now)
        val formData = createFormData(authCode, snsId, postNo)
        return webClient.post()
            .uri("/Sns/PostCommentCreate.aspx")
            .headers { headers -> headers.addAll(webClientHelper.setupHeaders()) }
            .body(formData)
            .exchangeToMono { it.bodyToMono(String::class.java) }
            .retryWhen(Retry.fixedDelay(3, Duration.ofMillis(3000))) // 6초 후에 1번만 재시도
            .map { objectMapper.readValue(it, WriteResponse::class.java) }
            .awaitSingle()
    }
}
