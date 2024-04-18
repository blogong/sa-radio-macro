package net.bk24.macro.core.domain.repository

import net.bk24.macro.core.domain.domain.SaAuth
import org.springframework.stereotype.Repository

@Repository
class SaRepositoryAdapter(
    private val saAuthJpaRepository: net.bk24.macro.core.infra.hibernate.SaAuthJpaRepository,
) : SaAuthRepository {
    override fun findAll(): List<SaAuth> {
        return saAuthJpaRepository.findAll()
    }
}
