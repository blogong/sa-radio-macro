package net.bk24.macro.core.repository

import net.bk24.macro.core.domain.SaAuth
import net.bk24.macro.infra.hibernate.SaAuthJpaRepository
import org.springframework.stereotype.Repository

@Repository
class SaRepositoryAdapter(
     private val saAuthJpaRepository: SaAuthJpaRepository
) : SaAuthRepository {
     override fun findAll(): List<SaAuth> {
          return saAuthJpaRepository.findAll()
     }
}