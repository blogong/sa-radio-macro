package net.bk24.macro.infra.clients

import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import reactor.netty.resources.ConnectionProvider
import java.time.Duration

@Configuration
class WebClientConfig {
     @Bean
     fun webClient(): WebClient {
          return WebClient.builder().baseUrl("https://sa.radio.com")
               .codecs { configurer -> configurer.defaultCodecs() }
               .clientConnector(ReactorClientHttpConnector(httpClient())).build()
     }

     @Bean
     fun httpClient(): HttpClient {
          return HttpClient.create().doOnConnected { conn ->
               conn.addHandlerLast(ReadTimeoutHandler(10))
               conn.addHandlerLast(WriteTimeoutHandler(10))
          }.responseTimeout(Duration.ofSeconds(5L))
     }

     @Bean
     fun connectionProvider(): ConnectionProvider {
          return ConnectionProvider.builder("customConnectionProvider").maxConnections(100)
               .maxIdleTime(Duration.ofMinutes(10)).maxLifeTime(Duration.ofMinutes(30)).build()
     }
}