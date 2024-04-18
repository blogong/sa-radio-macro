package net.bk24.macro.infra.socket

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.reactor.asFlux
import net.bk24.macro.core.business.LogSender
import org.springframework.stereotype.Component
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.WebSocketSession
import reactor.core.publisher.Mono

@Component
class SocketLogSender : WebSocketHandler, LogSender {
     private val logMessages = MutableSharedFlow<String>()

     override fun handle(session: WebSocketSession): Mono<Void> {
          val messageFlux =
               logMessages
                    .asFlux()
                    .map { session.textMessage(it) }

          return session.send(messageFlux)
     }

     override suspend fun execute(text: String) {
          logMessages.emit(text)
     }
}