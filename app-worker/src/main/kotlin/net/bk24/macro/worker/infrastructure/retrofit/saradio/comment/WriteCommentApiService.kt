package net.bk24.macro.worker.infrastructure.retrofit.saradio.comment

import net.bk24.macro.worker.infrastructure.retrofit.saradio.WriteResponse
import net.bk24.macro.worker.infrastructure.retrofit.saradio.helper.RandomStringGenerator
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface WriteCommentApiService {
    @FormUrlEncoded
    @POST("Sns/PostCommentCreate.aspx")
    suspend fun createComment(
        @Field("auth_code") authCode: String,
        @Field("post_no") postNo: Int,
        @Field("sns_user_sn") snsId: String,
        @Field("content") content: String = RandomStringGenerator.execute(),
        @Field("hash_tag_1") hashTag1: String = "",
        @Field("hash_tag_2") hashTag2: String = "",
        @Field("hash_tag_3") hashTag3: String = "",
        @Field("season_level_join_flag") seasonLevelJoinFlag: String = "Y",
        @Field("season_level_no") seasonLevelNo: String = "28",
        @Field("season_level_sn") seasonLevelSn: String = "1",
        @Field("youtube_url") youtubeUrl: String = "",
    ): Response<WriteResponse>
}
