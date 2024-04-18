package net.bk24.macro.core.clients

interface CommentWriter {
     suspend fun execute(
          authCodes: List<String>,
          concurrency: Int
     )
}