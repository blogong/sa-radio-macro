package net.bk24.macro.infra.hibernate

import net.bk24.macro.core.domain.SaAuth
import org.springframework.data.jpa.repository.JpaRepository

interface SaAuthJpaRepository : JpaRepository<SaAuth, Long>