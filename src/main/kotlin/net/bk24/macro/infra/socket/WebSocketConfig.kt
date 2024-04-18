package net.bk24.macro.infra.socket

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.HandlerMapping
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping
import org.springframework.web.reactive.socket.WebSocketHandler
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter

@Configuration
class WebSocketConfig(val socketLogSender: SocketLogSender) {
     @Bean
     fun webSocketHandlerAdapter() = WebSocketHandlerAdapter()

     @Bean
     fun handlerMapping(): HandlerMapping {
          val map: MutableMap<String, WebSocketHandler> = HashMap()
          map["/log"] = socketLogSender
          val handlerMapping = SimpleUrlHandlerMapping()
          handlerMapping.order = 1
          handlerMapping.urlMap = map
          return handlerMapping
     }
}