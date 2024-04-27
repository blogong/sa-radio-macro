package net.bk24.macro.core.application.writer

import GetNext5PostNo
import net.bk24.macro.common.ServerTodo
import net.bk24.macro.core.domain.repository.AuthRepository
import net.bk24.macro.core.infrastructure.client.webhook.TaskProvideClient
import net.bk24.macro.core.infrastructure.sqs.WorkerIpProperties
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CommentProvider(
    private val authRepository: AuthRepository,
    private val workerIpProps: WorkerIpProperties,
) {
    private val logger = LoggerFactory.getLogger("comment-provider")

    data class PostWriter(
        val snsId: String,
        val postId: String,
        val code: String,
    )

    @Transactional(readOnly = true)
    fun execute() {
        val auth = authRepository.findAll()
        val postWriters = auth.filter { it.postId?.isNotBlank() == true }
            .map { PostWriter(it.snsId!!, it.postId!!, it.code) }

        val ips = workerIpProps.ip

        for (ip in ips) {
            val index = ips.indexOf(ip)
            val postNos = GetNext5PostNo.execute(postWriters.map { it.postId }).map { it.first() }.take(5)
            sendRequestToWorker(ip, postWriters, postNos, index)
        }
    }

    private fun sendRequestToWorker(ip: String, postWriters: List<PostWriter>, postNos: List<Int>, index: Int) {
        logger.info("Sending request to $ip")
        val serverTodo = ServerTodo(
            authCodes = authRepository.findActiveCodesByChunkSize(8, index),
            snsIds = postWriters.map { it.snsId }.distinct(),
            postNos = postNos,
        )
        TaskProvideClient.execute(serverTodo, ip)
    }

    private fun AuthRepository.findActiveCodesByChunkSize(chunkSize: Int, index: Int): List<String> {
        return findAll().filter { !it.isBlocked }.map { it.code }.chunked(chunkSize).getOrElse(index) { emptyList() }
    }
}
