package net.bk24.macro.core.web.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.bk24.macro.core.domain.business.CommentService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PopularPostMacroController(
    private val commentService: CommentService,
) {
    @PostMapping("/api/macro/execute/")
    suspend fun start() {
        withContext(Dispatchers.IO) {
            commentService.writeComments()
        }
    }
}
