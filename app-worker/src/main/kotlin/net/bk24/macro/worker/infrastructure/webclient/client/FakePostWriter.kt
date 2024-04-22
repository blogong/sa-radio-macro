package net.bk24.macro.worker.infrastructure.webclient.client

import io.sentry.Sentry
import net.bk24.macro.worker.application.CreatePost
import net.bk24.macro.worker.infrastructure.discord.Bot
import net.bk24.macro.worker.localIpAddress
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("test")
@Component
class FakePostWriter : CreatePost {

    private val logger = LoggerFactory.getLogger("fake-post-writer")

    override suspend fun execute(writerId: String) {
        logger.info("[SERVER IP: $localIpAddress] 게시글 작성 계정 아이디: $writerId")
        Sentry.captureMessage("[SERVER IP: $localIpAddress] $writerId 계정이 ${writerId}번 포스트 작성 하였습니다.")
        Bot.send("[SERVER IP: $localIpAddress] 포스트 작성합니다")
    }
}
