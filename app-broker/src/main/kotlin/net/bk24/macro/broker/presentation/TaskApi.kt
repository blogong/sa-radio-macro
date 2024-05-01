package net.bk24.macro.broker.presentation

import net.bk24.macro.broker.application.writer.CommentProvider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskApi(
    private val commentProvider: CommentProvider,
) {
    @PostMapping("/sendTasks")
    fun sendTasks(): ResponseEntity<Void> {
        commentProvider.execute()
        return ResponseEntity.ok().build()
    }
}
