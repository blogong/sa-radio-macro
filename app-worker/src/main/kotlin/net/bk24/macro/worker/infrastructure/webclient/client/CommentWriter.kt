package net.bk24.macro.worker.infrastructure.webclient.client

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.bk24.macro.common.ServerTodo
import net.bk24.macro.worker.infrastructure.discord.Bot
import net.bk24.macro.worker.infrastructure.webclient.helper.WebClientHelper
import net.bk24.macro.worker.localIpAddress
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Profile("live")
@Component
class CommentWriter(
    webClient: WebClient,
    webClientHelper: WebClientHelper,
) : BaseCommentWriter(webClient, webClientHelper) {

    private val logger = LoggerFactory.getLogger("comment-writer")

    override suspend fun writeComment(serverTodo: ServerTodo) {
        coroutineScope {
            serverTodo.postNos.forEach { postNo ->
                launch {
                    writeCommentsForPost(serverTodo.authCodes, serverTodo.snsIds, postNo)
                }.join()
            }
        }
    }

    private suspend fun writeCommentsForPost(authCodes: List<String>, snsIds: List<String>, postNo: Int) {
        authCodes.zip(snsIds).forEach { (authCode, snsId) ->
            try {
                val response = sendCommentRequest(authCode, snsId, postNo)
                Bot.send("[SERVER IP: $localIpAddress] Response for post $postNo: $response")
                logger.info("Response for post $postNo: $response")
            } catch (e: Exception) {
                logger.error("Error posting comment for post $postNo: ${e.message}")
            }
        }
    }

    private suspend fun sendCommentRequest(authCode: String, snsId: String, postNo: Int): String {
        return webClient!!.post()
            .uri("/Sns/PostCommentCreate.aspx")
            .headers { headers -> headers.addAll(webClientHelper!!.setUpCommentHeaders()) }
            .body(WebClientHelper.createCommentFormData(authCode, snsId, postNo.toString()))
            .retrieve()
            .awaitBody<String>()
    }
}
