package net.bk24.macro.web.api.dto

import com.fasterxml.jackson.annotation.JsonProperty
import net.bk24.macro.core.domain.SaAuth
import java.time.LocalDateTime

data class SaAuthResponse(
     @JsonProperty("id")
     val id: Long?,
     @JsonProperty("code")
     val code: String,
     @JsonProperty("is_blocked")
     val isBlocked: Boolean,
     @JsonProperty("sns_id")
     val snsId: String?,
     @JsonProperty("post_no")
     val postNo: String?,
     @JsonProperty("memo")
     val memo: String?,
     @JsonProperty("created_at")
     val createdAt: LocalDateTime,
     @JsonProperty("updated_at")
     val updatedAt: LocalDateTime
) {
     companion object {
          fun SaAuth.toResponse() =
               SaAuthResponse(
                    id = this.id,
                    code = this.code,
                    isBlocked = this.isBlocked,
                    snsId = this.snsId,
                    postNo = this.postNo,
                    memo = this.memo,
                    createdAt = this.createdAt,
                    updatedAt = this.updatedAt,
               )
     }
}