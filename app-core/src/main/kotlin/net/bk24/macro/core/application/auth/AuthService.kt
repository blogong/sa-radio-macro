package net.bk24.macro.core.application.auth

import net.bk24.macro.core.domain.domain.Auth
import net.bk24.macro.core.domain.repository.AuthRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthService(private val authRepository: AuthRepository) {
    fun findAll(): List<Auth> {
        return authRepository.findAll()
    }

    fun findAllActiveAuthCount(): Int {
        return authRepository.findAllActiveCount()
    }
}
