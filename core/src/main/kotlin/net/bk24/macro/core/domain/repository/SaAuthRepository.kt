package net.bk24.macro.core.domain.repository

import net.bk24.macro.core.domain.domain.SaAuth

interface SaAuthRepository {
    fun findAll(): List<SaAuth>
}
