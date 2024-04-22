package net.bk24.macro.worker.application

interface CreatePost {
    suspend fun execute(writerId: String)
}
