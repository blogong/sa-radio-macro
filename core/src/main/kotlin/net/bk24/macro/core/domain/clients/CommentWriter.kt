package net.bk24.macro.core.domain.clients

interface CommentWriter {
    suspend fun execute(
        authCodes: List<String>,
    )
}
