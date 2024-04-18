package net.bk24.macro.core.business

import net.bk24.macro.core.clients.CommentWriter
import net.bk24.macro.core.repository.SaAuthRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentService(
     private val commentWriter: CommentWriter,
     private val authRepository: SaAuthRepository
) {
     @Transactional(readOnly = true)
     suspend fun writeComments(
          count: Int,
          concurrency: Int
     ) {
          val auths = authRepository.findAll().filter { !it.isBlocked }
          repeat(count) {
               commentWriter.execute(auths.map { it.code }, concurrency)
          }
     }
}