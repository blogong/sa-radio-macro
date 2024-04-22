package net.bk24.macro.worker.infrastructure.webclient.helper

import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.BodyInserters
import kotlin.random.Random

@Component
class WebClientHelper(
    private val commentProps: HttpHeaderCommentProperties,
    private val postProps: HttpHeaderPostProperties,
) {
    fun setUpCommentHeaders(): MultiValueMap<String, String> {
        val headers = LinkedMultiValueMap<String, String>()
        headers.add(HttpHeaders.ACCEPT, commentProps.accept)
        headers.add(HttpHeaders.CONNECTION, commentProps.connection)
        headers.add(HttpHeaders.ACCEPT_ENCODING, commentProps.acceptEncoding)
        headers.add(HttpHeaders.CONTENT_LENGTH, commentProps.contentLength)
        headers.add(HttpHeaders.COOKIE, commentProps.cookie)
        headers.add(HttpHeaders.CONTENT_TYPE, commentProps.contentType)
        headers.add(HttpHeaders.USER_AGENT, commentProps.userAgent)
        headers.add(HttpHeaders.HOST, commentProps.host)
        headers.add(HttpHeaders.ACCEPT_LANGUAGE, commentProps.acceptLanguage)
        return headers
    }

    fun setUpPostHeaders(): MultiValueMap<String, String> {
        val headers = LinkedMultiValueMap<String, String>()
        headers.add(HttpHeaders.ACCEPT, postProps.accept)
        headers.add(HttpHeaders.CONNECTION, postProps.connection)
        headers.add(HttpHeaders.ACCEPT_ENCODING, postProps.acceptEncoding)
        headers.add(HttpHeaders.CONTENT_LENGTH, postProps.contentLength)
        headers.add(HttpHeaders.COOKIE, postProps.cookie)
        headers.add(HttpHeaders.CONTENT_TYPE, postProps.contentType)
        headers.add(HttpHeaders.USER_AGENT, postProps.userAgent)
        headers.add(HttpHeaders.HOST, postProps.host)
        headers.add(HttpHeaders.ACCEPT_LANGUAGE, postProps.acceptLanguage)
        return headers
    }

    companion object {
        fun createCommentFormData(authCode: String, snsId: String, postNo: String): BodyInserters.FormInserter<String> {
            return BodyInserters.fromFormData("auth_code", authCode)
                .with("post_no", postNo)
                .with("sns_user_sn", snsId)
                .with("content", getRandomKoreanString())
                .with("season_level_join_flag", "Y")
                .with("season_level_sn", "1")
                .with("user_level", "47")
        }

        fun createPostFormData(authCode: String, content: String): BodyInserters.FormInserter<String> {
            return BodyInserters.fromFormData("auth_code", authCode)
                .with("content", content)
                .with("hash_tag_1", "")
                .with("hash_tag_2", "")
                .with("hash_tag_3", "")
                .with("season_level_join_flag", "Y")
                .with("season_level_no", "28")
                .with("season_level_sn", "1")
                .with("youtube_url", "")
        }

        fun getRandomKoreanString(): String {
            val start = 0xAC00
            val end = 0xD7A3
            val length = Random.nextInt(10, 51) // 글자 수를 10에서 50 사이로 랜덤하게 설정
            val words = mutableListOf<String>()

            for (i in 1..length) {
                val char = (Random.nextInt(end - start + 1) + start).toChar()
                words.add(char.toString())

                // 각 글자 사이에 랜덤하게 공백 추가 (약 20% 확률로 공백을 추가하여 문장을 구성)
                if (Random.nextInt(100) < 20 && i != length) {
                    words.add(" ")
                }
            }

            return words.joinToString("")
        }
    }
}
