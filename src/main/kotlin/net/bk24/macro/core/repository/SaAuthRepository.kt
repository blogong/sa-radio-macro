package net.bk24.macro.core.repository

import net.bk24.macro.core.domain.SaAuth

interface SaAuthRepository {
     fun findAll(): List<SaAuth>
}