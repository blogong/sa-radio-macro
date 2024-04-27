package net.bk24.macro.worker.infrastructure.retrofit.saradio

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WriteResponse(
    @SerialName("sp_rtn")
    val code: Long,
    @SerialName("error_msg")
    val errorMessage: String,
    @SerialName("is_scalar")
    val isScalar: String,
    @SerialName("row_count")
    val rowCount: Int,
)
