package net.bk24.macro.core.application

import net.bk24.macro.core.domain.domain.Auth
import net.bk24.macro.core.domain.repository.AuthRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AuthService(private val authRepository: AuthRepository) {
    @Transactional(readOnly = true)
    fun findAll(): List<Auth> {
        return authRepository.findAll()
    }
}
