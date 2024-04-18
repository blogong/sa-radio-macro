package net.bk24.macro.core.clients

interface PostWriter {
     suspend fun write(log: String)
}