package net.bk24.macro.worker.application.writer

// import net.bk24.macro.worker.infrastructure.webclient.PostClient
import PostClient.sendMultipartFormDataRequest
import net.bk24.macro.worker.application.CreatePost
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import kotlin.random.Random

@Profile("live")
@Component
class PostWriter : CreatePost {
    private val logger = LoggerFactory.getLogger("post-writer")

    override fun execute(authCode: String) {
        val url = "http://saradioapi.nexon.com/Sns/PostCreate.aspx"

        // 바운더리 생성
        val boundary = "6eKHrT-SN9MvpyapI3EiaKCXynkWZf"

        val content = Random.nextInt(1, 10)

        // 요청 바디 생성
        val requestBody = LinkedMultiValueMap<String, Any>()
        requestBody.add("hash_tag_3", "")
        requestBody.add("hash_tag_2", "")
        requestBody.add("hash_tag_1", "")
        requestBody.add("post_type", "1")
        requestBody.add("season_level_no", "0")
        requestBody.add("youtube_url", "")
        requestBody.add("level_no", "0")
        requestBody.add("season_level_join_flag", "Y")
        requestBody.add("content", "$content<emoticon>emoticon_img_bear_05</emoticon>\n극신용인 번개SP 거래 걱정마세요!\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n『 24시간 』 SP 판매 / 구매 / 밀봉판매\n[휴대폰 결제 , 카드결제 가능합니다]\nㄴ상담채널 > http://pf.kakao.com/_AepmG\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n1000명대 서든어택 단톡방 부방장\n거래내역 빵빵합니다!\n24시간 언제든지 상담문의 주세요!\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n【주의사항】\n└ 저는 서든라디오,카카오톡,페이스북 등\n절대 먼저 연락드리지 않아요\n└ 저를 사칭하는 사람이 거래유도를 한다면\n그 사람은 무조건 사기꾼 이에요 거래절대x\n└ 처음 거래 하시는분은 말씀해주세요\n친절하게 상담 드릴것을 약속합니다.\nㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n$content")
        requestBody.add("auth_code", authCode)

        // 클래스패스의 이미지 파일 로드
        val imageResource = ClassPathResource("cropped6219847526659768834.jpg")

        // 이미지 파일을 바이트 배열로 읽어와서 requestBody에 추가
        requestBody.add(
            "post_image",
            object : ByteArrayResource(imageResource.inputStream.readBytes()) {
                override fun getFilename(): String {
                    return "cropped6219847526659768834.jpeg"
                }
            },
        )

        // 요청 보내기
        val sendMultipartFormDataRequest = sendMultipartFormDataRequest(url, requestBody, boundary)
        logger.info("게시물 작성 완료 $sendMultipartFormDataRequest")
    }
}
