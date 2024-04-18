package net.bk24.macro.core.business

interface LogSender {
     suspend fun execute(text: String)
}