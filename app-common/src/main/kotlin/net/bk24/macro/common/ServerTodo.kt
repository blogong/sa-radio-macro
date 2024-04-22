package net.bk24.macro.common

import java.util.UUID

data class ServerTodo(
    val id: UUID = UUID.randomUUID(),
    val writerId: String = "",
    val authCodes: List<String> = emptyList(),
    val snsIds: List<String> = emptyList(),
    var postNos: List<Int> = emptyList(),
    val type: Type = Type.COMMENT,
    val workerIp: String = "",
) {
    enum class Type {
        POST,
        COMMENT,
    }
}
