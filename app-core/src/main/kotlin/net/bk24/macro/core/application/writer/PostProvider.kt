package net.bk24.macro.core.application.writer

import net.bk24.macro.common.ServerTodo
import net.bk24.macro.core.domain.repository.AuthRepository
import net.bk24.macro.core.infrastructure.client.webhook.TaskProvideClient
import net.bk24.macro.core.infrastructure.sqs.WorkerIpProperties
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
            authCodes = postWriter,
        )
        return TaskProvideClient.execute(todo, lastIp)
    }
}
