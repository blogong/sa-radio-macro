package net.bk24.macro.core.presentation.api

import net.bk24.macro.core.application.AuthService
import net.bk24.macro.core.presentation.api.dto.AuthResponse
import net.bk24.macro.core.presentation.api.dto.AuthResponse.Companion.toResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthApi(
    private val authService: AuthService,
) {
    @GetMapping("/api/sa/auth")
    fun auth(): List<AuthResponse> {
        return authService.findAll().map { saAuth ->
            saAuth.toResponse()
        }
    }
}
