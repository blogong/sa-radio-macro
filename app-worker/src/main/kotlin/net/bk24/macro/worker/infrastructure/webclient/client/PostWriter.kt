package net.bk24.macro.worker.infrastructure.webclient.client

import net.bk24.macro.worker.application.CreatePost
import net.bk24.macro.worker.infrastructure.discord.Bot
import net.bk24.macro.worker.infrastructure.webclient.helper.WebClientHelper
import net.bk24.macro.worker.infrastructure.webclient.helper.WebClientHelper.Companion.createPostFormData
import net.bk24.macro.worker.infrastructure.webclient.helper.WebClientHelper.Companion.getRandomKoreanString
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody

@Profile("live")
@Component
class PostWriter(
    private val webClient: WebClient,
    private val webClientHelper: WebClientHelper,
) : CreatePost {

    private val logger = LoggerFactory.getLogger("post-writer")

    override suspend fun execute(writerId: String) {
        logger.info("게시글 등록 $writerId")
        Bot.send("게시글 등록 $writerId")
        val awaitBody = webClient.post()
            .uri("/Sns/PostCreate.aspx")
            .headers { headers -> headers.addAll(webClientHelper.setUpPostHeaders()) }
            .body(createPostFormData(writerId, getRandomKoreanString()))
            .retrieve()
            .awaitBody<String>()
        logger.info("게시글 등록: $awaitBody")
    }
}
