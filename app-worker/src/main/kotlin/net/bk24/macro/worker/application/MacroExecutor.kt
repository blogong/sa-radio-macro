package net.bk24.macro.worker.application

import net.bk24.macro.common.ServerTodo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class MacroExecutor(
    private val commentWriter: CreateComment,
    private val createPost: CreatePost,
) {

    private val logger = LoggerFactory.getLogger("macro-executor")

    suspend fun scheduleTask(serverTodo: ServerTodo) {
        when (serverTodo.type) {
            ServerTodo.Type.POST -> {
                createPost.execute(serverTodo.writerId)
            }

            ServerTodo.Type.COMMENT -> {
                logger.info("macro executor 댓글 라우팅 실행")

                commentWriter.execute(serverTodo)
            }
        }
    }
}
