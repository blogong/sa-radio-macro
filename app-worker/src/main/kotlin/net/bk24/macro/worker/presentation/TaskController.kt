package net.bk24.macro.worker.presentation

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import net.bk24.macro.worker.model.Auth
import net.bk24.macro.worker.model.AuthRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController(
    private val authRepository: AuthRepository,
) {

    @GetMapping("/all")
    suspend fun getAll(): Flow<Auth> {
        return authRepository.findAll().asFlow()
    }
}
