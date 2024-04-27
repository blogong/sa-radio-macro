package net.bk24.macro.common

import kotlinx.serialization.Serializable

@Serializable
data class ServerTodo(
    val writerId: String = "",
    var authCodes: List<String> = emptyList(),
    val snsIds: List<String> = emptyList(),
    var postNos: List<Int> = emptyList(),
    val type: Type = Type.COMMENT,
) {

    enum class Type {
        POST,
        COMMENT,
    }

    fun remix() {
        this.authCodes = this.authCodes.shuffled()
    }
}
