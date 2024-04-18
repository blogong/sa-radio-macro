package net.bk24.macro.core.infra.clients

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.withContext
import net.bk24.macro.core.domain.clients.CommentWriter
import net.bk24.macro.core.infra.clients.WebClientHelper.Companion.createFormData
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient

private val logger = LoggerFactory.getLogger("comment-client")

@Component
class CommentClient(
    private val webClient: WebClient,
    private val webClientHelper: net.bk24.macro.core.infra.clients.WebClientHelper,
) : CommentWriter {

    override suspend fun execute(authCodes: List<String>) {
        val chunkSize = 5 // 적절한 청크 크기 설정
        val responseFlow: Flow<String> = authCodes.chunked(chunkSize)
            .asFlow()
            .flatMapConcat { chunk ->
                makeRequestInParallel(chunk)
            }
            .flowOn(Dispatchers.IO)

        withContext(Dispatchers.IO) {
            responseFlow.collect { response ->
                logger.info("Response: $response")
            }
        }
    }

    private fun makeRequestInParallel(chunk: List<String>): Flow<String> {
        return chunk.asFlow().map { code ->
            makeRequest("/Sns/PostCommentCreate.aspx", code)
        }
    }

    private suspend fun makeRequest(
        url: String,
        authCode: String,
    ): String {
        return webClient.post()
            .uri(url)
            .headers { headers -> headers.addAll(webClientHelper.setupHeaders()) }
            .body(createFormData(authCode))
            .retrieve()
            .bodyToMono(String::class.java)
            .awaitFirstOrNull() ?: throw RuntimeException("Empty response")
    }
}
