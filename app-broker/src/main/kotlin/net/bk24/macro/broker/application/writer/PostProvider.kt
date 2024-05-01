package net.bk24.macro.broker.application.writer

import net.bk24.macro.broker.domain.repository.AuthRepository
import net.bk24.macro.broker.infrastructure.client.TaskClient
import net.bk24.macro.broker.infrastructure.client.WorkerIpProperties
import net.bk24.macro.common.ServerTodo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class PostProvider(
    private val workerIpProps: WorkerIpProperties,
    private val authRepository: AuthRepository,
) {

    private val logger = LoggerFactory.getLogger("post-provider")

    @Transactional(readOnly = true)
    fun execute() {
        logger.info("Send post")
        val postWriter = authRepository
            .findAll()
            .filter { it.postId?.isNotBlank() == true }
            .map { it.code }

        val lastIp = workerIpProps.ip.last()
        val todo = ServerTodo(
            type = ServerTodo.Type.POST,
            codes = postWriter,
        )
        return TaskClient.execute(lastIp, todo)
    }
}
