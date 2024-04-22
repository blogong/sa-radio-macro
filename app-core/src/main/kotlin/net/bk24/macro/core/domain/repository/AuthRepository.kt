package net.bk24.macro.core.domain.repository

import net.bk24.macro.core.domain.domain.Auth

interface AuthRepository {
    fun findAll(): MutableList<Auth>
    fun findSnsIdNotNull(): List<Auth>
    fun save(auth: Auth)
}
