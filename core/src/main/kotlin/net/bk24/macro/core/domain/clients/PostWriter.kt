package net.bk24.macro.core.domain.clients

interface PostWriter {
    suspend fun write(log: String)
}
