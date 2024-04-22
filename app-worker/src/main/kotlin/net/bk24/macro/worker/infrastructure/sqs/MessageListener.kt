package net.bk24.macro.worker.infrastructure.sqs

import com.fasterxml.jackson.databind.ObjectMapper
import io.awspring.cloud.sqs.annotation.SqsListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import net.bk24.macro.common.ServerTodo
import net.bk24.macro.worker.application.MacroExecutor
import net.bk24.macro.worker.infrastructure.discord.Bot
import net.bk24.macro.worker.infrastructure.webclient.helper.NetworkHelper
import org.springframework.stereotype.Component
import java.util.concurrent.Executors

@Component
class MessageListener(
    private val macroExecutor: MacroExecutor,
    private val objectMapper: ObjectMapper,
) {
    companion object {
        var localIpAddress: String? = null // 로컬 IP 주소
    }

    // 사용자 정의 쓰레드 풀 생성
    private val customThreadPool = Executors.newFixedThreadPool(4).asCoroutineDispatcher()
    private val scope = CoroutineScope(customThreadPool) // 쓰레드 풀을 사용하는 CoroutineScope 생성

    @SqsListener("macro.fifo")
    fun receiveMessage(message: String) {
        scope.launch {
            Bot.send("[SERVER IP: $localIpAddress] $message")
            // 로컬 IP 주소를 초기화하지 못한 경우, 재시도
            localIpAddress = localIpAddress ?: NetworkHelper.getLocalIpAddress()

            val serverTodo = objectMapper.readValue(message, ServerTodo::class.java)

            if (shouldProcessMessageOnThisServer(serverTodo)) {
                macroExecutor.scheduleTask(serverTodo)
            }
        }
    }

    private fun shouldProcessMessageOnThisServer(serverTodo: ServerTodo): Boolean {
        // 메시지의 workerIp와 로컬 IP 주소를 비교
        return serverTodo.workerIp == localIpAddress
    }
}
