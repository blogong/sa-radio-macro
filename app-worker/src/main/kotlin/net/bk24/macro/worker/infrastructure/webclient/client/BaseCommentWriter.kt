package net.bk24.macro.worker.infrastructure.webclient.client

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlinx.coroutines.sync.withPermit
import net.bk24.macro.common.ServerTodo
import net.bk24.macro.worker.application.CreateComment
import net.bk24.macro.worker.infrastructure.webclient.helper.WebClientHelper
import org.slf4j.LoggerFactory
import org.springframework.web.reactive.function.client.WebClient
import java.time.Duration
import java.time.Instant

abstract class BaseCommentWriter(
    protected val webClient: WebClient? = null,
    protected val webClientHelper: WebClientHelper? = null,
) : CreateComment {
    private val logger = LoggerFactory.getLogger(this::class.java)
    private val semaphore = Semaphore(10) // 동시에 처리할 수 있는 최대 작업 수

    abstract suspend fun writeComment(serverTodo: ServerTodo)

    override suspend fun execute(serverTodo: ServerTodo) {
        coroutineScope {
            // 각 authCode에 대한 비동기 작업 리스트 생성, Semaphore를 사용하여 동시성 제어
            serverTodo.authCodes.map { authCode ->
                launch {
                    semaphore.withPermit {
                        val startTime = Instant.now()
                        writeComment(serverTodo)

                        val endTime = Instant.now()
                        val duration = Duration.between(startTime, endTime)
                        logger.info("작업 수행 시간: ${duration.seconds}초, 댓글 작성완료 서버 인덱스: ${serverTodo.workerIp}, 계정 정보: $authCode")
                    }
                }
            }.forEach { it.join() } // 모든 작업이 완료될 때까지 기다림
        }
    }
}
