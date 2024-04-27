package net.bk24.macro.worker.presentation

import net.bk24.macro.common.ServerTodo
import net.bk24.macro.worker.application.CreateComment
import net.bk24.macro.worker.application.CreatePost
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TaskController(
    private val createComment: CreateComment,
    private val createPost: CreatePost,
) {

    @PostMapping("/task/execute")
    fun executeTask(@RequestBody serverTodo: ServerTodo): ResponseEntity<Void> {
        println("요청을 받았습니다")
        when (serverTodo.type) {
            ServerTodo.Type.POST -> {
                createPost.execute(serverTodo.authCodes[0])
            }

            ServerTodo.Type.COMMENT -> {
                try {
                    createComment.execute(serverTodo)
                } catch (e: Exception) {
                    println(e)
                }
            }
        }
        return ResponseEntity.ok().build()
    }
}
