package net.bk24.macro.worker.application.writer

import com.github.benmanes.caffeine.cache.Cache
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import net.bk24.macro.common.DiscordBot
import net.bk24.macro.common.ServerTodo
import net.bk24.macro.worker.application.CreateComment
import net.bk24.macro.worker.application.LogStore
import net.bk24.macro.worker.infrastructure.client.CommentClient
import net.bk24.macro.worker.infrastructure.client.WriteResponse
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class CommentWriter(
    private val commentClient: CommentClient,
    private val authCodeCache: Cache<String, Long>,
) : CreateComment {
    private val logger = LoggerFactory.getLogger("comment-writer")
    private val blockedSet = mutableSetOf<String>()

    override suspend fun execute(serverTodo: ServerTodo) {
        blockedSet.clear()
        LogStore.initialize()

        val virtualThreadDispatcher = VirtualThreadManager.executorService.asCoroutineDispatcher()
        val semaphore: Semaphore = Semaphore(8) // 동시에 8개의 작업만 허용

        coroutineScope {
            launch {
                DiscordBot.sendMessage("starts writing comments")
            }.join()
            launch(virtualThreadDispatcher) {
                semaphore.withPermit {
                    workUntilTimeOver(serverTodo)
                }
            }.start()
        }
    }

    override fun shutdown() {
        VirtualThreadManager.shutdownAndReset()
    }

    private suspend fun CommentWriter.workUntilTimeOver(serverTodo: ServerTodo) {
        val commentWriteStartTime = System.currentTimeMillis() // 시작 시간 기록
        val famousPostCommentTimeLimit = 28 * 60 * 1000 // 30분을 밀리초로 변환

        while (System.currentTimeMillis() - commentWriteStartTime < famousPostCommentTimeLimit) {
            serverTodo.codes.forEach { code ->
                sendCommentRequest(code, serverTodo)
            }
        }
        logger.info("time over")
    }

    private suspend fun sendCommentRequest(
        code: String,
        serverTodo: ServerTodo,
    ) {
//        logger.info("send comment request")
        val res = commentClient.makeRequest(
            authCode = code,
            snsId = serverTodo.snsIds.first(),
            postNo = serverTodo.postNos.first().toString(),
        )

//        delayBetweenCommentRequest()
        logResponse(res, code)
        logIfBlocked(res, code)

        againIfCached(res)
    }

    private fun againIfCached(res: WriteResponse?) {
        if (res == null) {
            return
        }
    }

    private suspend fun logIfBlocked(res: WriteResponse?, code: String) {
        if (res !== null && res.errorMessage.isNotBlank()) {
            VirtualThreadManager.shutdownAndReset()
            DiscordBot.sendMessage("code: $code kicked, server stop")
//                LogStore.record()
//                blockedSet.add(code)
//                logger.info("${blockedSet.size}")
//                logger.info("$blockedSet")
        }
    }

    private suspend fun delayBetweenCommentRequest() {
//        val random = Random.nextLong(100, 150)
//        delay(random) // 요청 사이에 딜레이 (필요에 따라 조정)
    }

    private suspend fun logResponse(res: WriteResponse?, code: String) {
        if (res !== null) {
            logger.info(code)
            logger.info(res.errorMessage)
        }
    }
}
