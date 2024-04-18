package net.bk24.macro.web.api

import net.bk24.macro.core.business.SaAuthService
import net.bk24.macro.web.api.dto.SaAuthResponse
import net.bk24.macro.web.api.dto.SaAuthResponse.Companion.toResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class SaAuthController(
     private val saAuthService: SaAuthService
) {
     @GetMapping("/api/sa/auth")
     fun auth(): List<SaAuthResponse> {
          return saAuthService.findAll().map { saAuth ->
               saAuth.toResponse()
          }
     }
}