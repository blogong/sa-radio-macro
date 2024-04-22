package net.bk24.macro.worker.infrastructure.webclient.client

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.bk24.macro.common.ServerTodo
import net.bk24.macro.worker.infrastructure.discord.Bot
import net.bk24.macro.worker.localIpAddress
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component

@Profile("test")
@Component
class FakeCommentWriter : BaseCommentWriter() {
    override suspend fun writeComment(serverTodo: ServerTodo) {
        val postNos = listOf(serverTodo.postNos) // Assume this list is populated with the correct post numbers

        coroutineScope {
            postNos.forEach { postNo ->
                launch {
                    Bot.send("[SERVER IP: $localIpAddress] ${postNo}번째 글의 댓글 작성합니다")
                }
            }
        }
    }
}
