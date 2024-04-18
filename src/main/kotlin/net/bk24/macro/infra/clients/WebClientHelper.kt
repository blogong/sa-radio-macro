package net.bk24.macro.infra.clients

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import kotlin.random.Random

@Component
class WebClientHelper(private val httpHeaderProperties: HttpHeaderProperties) {
     fun setupHeaders(): MultiValueMap<String, String> {
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

     companion object {
          fun createFormData(authCode: String): BodyInserters.FormInserter<String> {
               return BodyInserters.fromFormData("auth_code", authCode)
                    .with("post_no", "33")
                    .with("sns_user_sn", "233123")
                    .with("content", getRandomKoreanString())
                    .with("season_level_join_flag", "Y")
                    .with("season_level_sn", "1")
                    .with("user_level", "47")
          }

          private fun getRandomKoreanString(length: Int = 15): String {
               val start = 0xAC00
               val end = 0xD7A3
               return (1..length)
                    .map { (Random.nextInt(end - start + 1) + start).toChar() }
                    .joinToString("")
          }
     }
}