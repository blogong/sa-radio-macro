package net.bk24.macro.worker.infrastructure.retrofit.saradio.post

import net.bk24.macro.worker.infrastructure.retrofit.saradio.WriteResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WritePostApiService {
    @FormUrlEncoded
    @POST("Sns/PostCreate.aspx")
    fun createPost(
        @Field("auth_code") authCode: String,
        @Field("content") content: String = "<emoticon>emoticon_img_bear_05</emoticon>\n" +
            " 극신용인 번개SP 거래 걱정마세요!\n" +
            " ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n" +
            " 『 24시간 』 SP 판매 / 구매 / 밀봉판매\n" +
            " [휴대폰 결제 , 카드결제 가능합니다]\n" +
            " ㄴ상담채널 > http://pf.kakao.com/_AepmG\n" +
            " ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n" +
            " 1000명대 서든어택 단톡방 부방장\n" +
            " 거래내역 빵빵합니다!\n" +
            " 24시간 언제든지 상담문의 주세요!\n" +
            " ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n" +
            " 【주의사항】\n" +
            " └ 저는 서든라디오,카카오톡,페이스북 등\n" +
            " 절대 먼저 연락드리지 않아요\n" +
            " └ 저를 사칭하는 사람이 거래유도를 한다면\n" +
            " 그 사람은 무조건 사기꾼 이에요 거래절대x\n" +
            " └ 처음 거래 하시는분은 말씀해주세요\n" +
            " 친절하게 상담 드릴것을 약속합니다.\n" +
            " ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ\n" +
            " <emoticon>emoticon_img_bear_05</emoticon>",
        @Field("hash_tag_1") hashTag1: String = "",
        @Field("hash_tag_2") hashTag2: String = "",
        @Field("hash_tag_3") hashTag3: String = "",
        @Field("season_level_join_flag") seasonLevelJoinFlag: String = "Y",
        @Field("season_level_no") seasonLevelNo: String = "28",
        @Field("season_level_sn") seasonLevelSn: String = "1",
        @Field("youtube_url") youtubeUrl: String = "",
    ): Call<WriteResponse>
}
