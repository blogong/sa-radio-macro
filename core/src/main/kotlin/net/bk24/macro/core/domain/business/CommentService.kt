package net.bk24.macro.core.domain.business

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import net.bk24.macro.core.domain.clients.CommentWriter
import net.bk24.macro.core.domain.repository.SaAuthRepository
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val logger = LoggerFactory.getLogger("comment-service")

@Service
class CommentService(
    private val commentWriter: CommentWriter,
    private val authRepository: SaAuthRepository,
) {
    @Transactional(readOnly = true)
    suspend fun writeComments() {
        val chunks = authRepository.findAll().filter { !it.isBlocked }.filter { it.processNo == 1 }.map { it.code }

        coroutineScope {
            launch {
                commentWriter.execute(chunks)
            }
        }
    }
}
