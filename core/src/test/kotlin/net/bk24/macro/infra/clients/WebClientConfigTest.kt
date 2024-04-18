package net.bk24.macro.infra.clients

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import net.bk24.macro.domain.business.SaAuthService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.test.context.ActiveProfiles
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import kotlin.random.Random

@SpringBootTest
@ActiveProfiles("dev")
class WebClientConfigTest {
    @Autowired
    lateinit var webClient: WebClient

    @Autowired
    lateinit var httpHeaderProperties: net.bk24.macro.core.infra.clients.HttpHeaderProperties

    @Autowired
    lateinit var saAuthService: SaAuthService

    @Test
    fun sendConcurrentRequests() =
        runTest {
            val auths = saAuthService.findAll().filter { !it.isBlocked }

            repeat(1) { // 무한 반복
                var responseCount = 0
                val startTime = System.currentTimeMillis()
                (auths).asFlow()
                    .map {
                        async {
                            makeRequest(
                                "/Sns/PostCommentCreate.aspx",
                                it.code,
                            )
                        }
                    }
                    .collect {
                        println(it.await())
                        responseCount++
                    }
                val duration = System.currentTimeMillis() - startTime
                println("Total duration: $duration ms")
                println("Total responses: $responseCount")

                delay(6000) // 6초 간격으로 반복
            }
        }

    private suspend fun makeRequest(
        url: String,
        authCode: String,
    ): String {
        val formData =
            BodyInserters.fromFormData("auth_code", authCode)
                .with("post_no", "26")
                .with("sns_user_sn", "233123")
                .with("content", getRandomKoreanString())
                .with("season_level_join_flag", "Y")
                .with("season_level_sn", "1")
                .with("user_level", "47")

        return webClient.post().uri(url).headers { x ->
            x.addAll(setupHeaders())
        }.body(formData).retrieve().awaitBody<String>()
    }

    private fun setupHeaders(): MultiValueMap<String, String> {
        val headers = LinkedMultiValueMap<String, String>()
        headers.add(HttpHeaders.ACCEPT, httpHeaderProperties.accept)
        headers.add(HttpHeaders.CONNECTION, httpHeaderProperties.connection)
        headers.add(HttpHeaders.ACCEPT_ENCODING, httpHeaderProperties.acceptEncoding)
        headers.add(HttpHeaders.CONTENT_LENGTH, httpHeaderProperties.contentLength)
        headers.add(HttpHeaders.COOKIE, httpHeaderProperties.cookie)
        headers.add(HttpHeaders.CONTENT_TYPE, httpHeaderProperties.contentType)
        headers.add(HttpHeaders.USER_AGENT, httpHeaderProperties.userAgent)
        headers.add(HttpHeaders.HOST, httpHeaderProperties.host)
        headers.add(HttpHeaders.ACCEPT_LANGUAGE, httpHeaderProperties.acceptLanguage)
        return headers
    }

    private fun getRandomKoreanString(length: Int = 15): String {
        val start = 0xAC00
        val end = 0xD7A3
        return (1..length)
            .map { (Random.nextInt(end - start + 1) + start).toChar() }
            .joinToString("")
    }
}
