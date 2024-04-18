package net.bk24.macro.core.infra.clients

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
    fun httpHeaderProperties(): net.bk24.macro.core.infra.clients.HttpHeaderProperties {
        return net.bk24.macro.core.infra.clients.HttpHeaderProperties()
    }

    @Bean
    fun webClient(): WebClient {
        return WebClient.builder()
            .baseUrl("https://saradioapi.nexon.com")
            .codecs { configurer -> configurer.defaultCodecs().maxInMemorySize(-1) }
            .clientConnector(ReactorClientHttpConnector(httpClient()))
            .build()
    }

    @Bean
    fun httpClient(): HttpClient {
        val connectionProvider =
            ConnectionProvider.builder("custom")
                .maxConnections(500) // 최대 커넥션 수 설정
                .build()

        val httpClient =
            HttpClient.create(connectionProvider)
                .doOnConnected { conn ->
                    conn.addHandlerLast(ReadTimeoutHandler(-1))
                    conn.addHandlerLast(WriteTimeoutHandler(-1))
                }.responseTimeout(Duration.ofSeconds(-1))
                .wiretap(true)

        return httpClient
    }

    @Bean
    fun connectionProvider(): ConnectionProvider {
        return ConnectionProvider.builder("customConnectionProvider").maxConnections(100)
            .maxIdleTime(Duration.ofMinutes(10)).maxLifeTime(Duration.ofMinutes(30)).build()
    }
}
