package net.bk24.macro.core.presentation.api

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import net.bk24.macro.core.application.TodoProvider
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TodoApi(
    private val todoProvider: TodoProvider,
) {
    @PostMapping("/sendTasks")
    suspend fun sendTasks(
        @RequestParam("loop") loop: Int,
        @RequestParam("content") content: String,
        @RequestParam("chunkSize") chunkSize: Int,
        @RequestParam("delay") delay: Long,
    ) {
        coroutineScope {
            async {
                todoProvider.sendMessageToQueue()
            }.await()
        }
    }
}
