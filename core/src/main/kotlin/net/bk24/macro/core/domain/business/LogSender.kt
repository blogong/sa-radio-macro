package net.bk24.macro.core.domain.business

interface LogSender {
    suspend fun execute(text: String)
}
