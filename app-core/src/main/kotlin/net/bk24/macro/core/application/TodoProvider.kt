package net.bk24.macro.core.application

import GetNext5PostNo
import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.future.await
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import net.bk24.macro.common.ServerTodo
import net.bk24.macro.core.domain.repository.AuthRepository
import net.bk24.macro.core.infrastructure.sqs.WorkerIpProperties
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import software.amazon.awssdk.services.sqs.SqsAsyncClient
import java.time.Duration.between
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.UUID

@Service
class TodoProvider(
    private val sqsAsyncClient: SqsAsyncClient,
    private val objectMapper: ObjectMapper,
    private val authRepository: AuthRepository,
    private val workerIpProps: WorkerIpProperties,
) {

    private val logger = LoggerFactory.getLogger("task-provider")

    @Transactional
    fun sendMessageToQueue() = runBlocking {
        val auth = authRepository.findAll()

        val writerIds = auth.filter { it.snsId?.isNotBlank() == true }.map { it.snsId!! }
        val postIds = auth.filter { it.postId?.isNotBlank() == true }.map { it.postId!! }
        val authCodes = auth.filter { !it.isBlocked }.map { it.code }
        val serverCount = workerIpProps.ip.size

        println(writerIds)
        println(postIds)

        val commentsPerServer = (authCodes.size + serverCount - 1) / serverCount

        val isTimeTest = true

        val tasks = mutableListOf<Pair<LocalDateTime, suspend () -> Unit>>()

        /**
         * 서버 대수 만큼 게시글 작성
         *
         * 예시) 계정 5개, 서버 5개 = 5개 게시글
         */
//        println(serverCount)
//
//        async {
//            writerIds.forEachIndexed { index, s ->
//                writePost(s, workerIpProps.ip[index])
//                delay(2000)
//            }
//
//            writerIds.forEachIndexed { index, s ->
//                writePost(s, workerIpProps.ip[index])
//                delay(3000)
//            }
//        }.await()

//         댓글 작성 작업 추가
//        GetNext5PostNo.execute(postIds).forEach { postNos ->
//            tasks.add(
//                TimeHelper.getNextMinuteMark(0) to {
//                },
//            )
//        }

        waitForTimeAndRun(true, LocalDateTime.now()) { writeComment(commentsPerServer, authCodes, writerIds, GetNext5PostNo.execute(postIds)[1]) }
        // 서버 대수에 따라 작업을 분할하여 실행
//        val chunkedTasks = tasks.chunked(serverCount)
//
//        chunkedTasks.forEach { taskChunk ->
//            launch {
//                taskChunk.forEach { (targetTime, task) ->
//                    waitForTimeAndRun(isTimeTest, targetTime, task)
//                }
//            }
//        }
    }

    private suspend fun writeComment(
        commentsPerServer: Int,
        authCodes: List<String>,
        snsIds: List<String>,
        postNo: List<Int>,
    ) {
        coroutineScope {
            workerIpProps.ip.forEachIndexed { index, ip ->
                launch {
                    val start = index * commentsPerServer
                    val end = minOf(start + commentsPerServer, authCodes.size)
                    val chunk = authCodes.subList(start, end)
                    sendComments(chunk, snsIds, ip, postNo)
                }
            }
            delay(1000 * 8000)
        }
    }

    suspend fun waitForTimeAndRun(test: Boolean, targetDateTime: LocalDateTime, executeTask: suspend () -> Unit) {
        if (test) {
            executeTask()
            delay(3000L)
            return
        }

        while (true) {
            val now = LocalDateTime.now()
            if (now.isAfter(targetDateTime) || now.isEqual(targetDateTime)) {
                println("It's the specific time: $targetDateTime, running the task...")
                executeTask()
                break
            }
            val secondsToWait = now.until(targetDateTime, ChronoUnit.SECONDS)
            println("Waiting for $secondsToWait seconds until it's time to run the task.")
            delay(secondsToWait * 1000)
        }
        delay(3000)
    }

    private suspend fun writePost(writerId: String, workerIp: String) {
        sendSqsMessage(
            ServerTodo(
                writerId = writerId,
                type = ServerTodo.Type.POST,
                workerIp = workerIp,
            ),
        )
        delay(3000)
    }

    private suspend fun sendComments(
        authCodes: List<String>,
        snsIds: List<String>,
        workerIp: String,
        postNos: List<Int>,
    ) {
        val serverTodo = ServerTodo(
            authCodes = authCodes,
            snsIds = snsIds,
            workerIp = workerIp,
            postNos = postNos,
        )
        sendSqsMessage(serverTodo)
    }

    private suspend fun sendSqsMessage(serverTodo: ServerTodo) {
        sqsAsyncClient.sendMessage {
            it.queueUrl("https://sqs.ap-northeast-2.amazonaws.com/339713191287/macro.fifo")
            it.messageBody(objectMapper.writeValueAsString(serverTodo))
            it.messageGroupId("group-${serverTodo.workerIp}") // IP 주소를 그룹 ID로 사용
            it.messageDeduplicationId(UUID.randomUUID().toString())
        }.await()
        logger.info("Message sent: Worker IP ${serverTodo.workerIp}")
    }

    private suspend fun delayUntilExactMinute(minute: Long) {
        val current = LocalDateTime.now()
        val next = current.truncatedTo(ChronoUnit.HOURS).plusMinutes(minute * ((current.minute / minute) + 1))
        delay(between(current, next).toMillis())
    }
}
