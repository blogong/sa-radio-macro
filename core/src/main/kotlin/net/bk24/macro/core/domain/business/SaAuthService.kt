package net.bk24.macro.core.domain.business

import net.bk24.macro.core.domain.domain.SaAuth
import net.bk24.macro.core.domain.repository.SaAuthRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaAuthService(private val saAuthRepository: SaAuthRepository) {
    @Transactional(readOnly = true)
    fun findAll(): List<SaAuth> {
        return saAuthRepository.findAll()
    }
}
