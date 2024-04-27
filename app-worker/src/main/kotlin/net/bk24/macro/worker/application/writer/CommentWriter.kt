package net.bk24.macro.worker.application.writer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.bk24.macro.common.ServerTodo
import net.bk24.macro.worker.application.CreateComment
import net.bk24.macro.worker.infrastructure.webclient.CommentClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class CommentWriter(
    private val commentClient: CommentClient,
) : CreateComment {
    private val logger = LoggerFactory.getLogger("comment-writer")
    private val bloked = mutableSetOf<String>()

    override fun execute(serverTodo: ServerTodo) {
        runBlocking {
            launch(Dispatchers.IO) {
                val startTime = System.currentTimeMillis() // 시작 시간 기록
                val duration = 28 * 60 * 1000 // 30분을 밀리초로 변환

                while (System.currentTimeMillis() - startTime < duration) {
                    serverTodo.authCodes.forEach { code ->
                        val res = commentClient.makeRequest(
                            authCode = code,
                            snsId = serverTodo.snsIds[0],
                            postNo = serverTodo.postNos[0].toString(),
                        )
                        val random = Random.nextLong(50, 80)
                        delay(random) // 요청 사이에 딜레이 (필요에 따라 조정)

                        if (res !== null) {
                            logger.info(code)
                            logger.info(res.errorMessage)
                        }

                        if (res !== null && res.errorMessage.isBlank()) {
                            bloked.add(code)
                            logger.info("${bloked.size}")
                            logger.info("$bloked")
                        }

                        if (res == null) {
                            return@forEach
                        }
                    }
                }
            }.join()
        }
    }
}
