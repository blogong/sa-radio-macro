package net.bk24.macro.core.infrastructure.hibernate

import net.bk24.macro.core.domain.domain.Auth
import org.springframework.data.jpa.repository.JpaRepository

interface AuthJpaRepository : JpaRepository<Auth, Long> {
    fun findSaAuthBySnsIdNotNull(): List<Auth>
}
