package net.bk24.macro.web.api

import net.bk24.macro.core.business.CommentService
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PopularPostMacroController(
//     private val macroService: MacroService,
     private val commentService: CommentService
) {
     @PostMapping("/api/macro/execute/{count}/{concurrency}")
     suspend fun start(
          @PathVariable count: Int,
          @PathVariable concurrency: Int
     ) {
          commentService.writeComments(count, concurrency)
//          coroutineScope {
//               macroService.startPopularPostMacro()
//          }
     }
}