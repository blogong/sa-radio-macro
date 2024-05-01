package net.bk24.macro.broker.application

import net.bk24.macro.broker.application.writer.CommentProvider
import net.bk24.macro.broker.application.writer.PostProvider
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.stereotype.Component
import java.time.LocalTime
import java.util.PriorityQueue

@Component
@EnableScheduling
class TaskMacroExecutor(
    private val postProvider: PostProvider,
    private val commentProvider: CommentProvider,
) {

    private val taskQueue: PriorityQueue<ScheduledTask> = PriorityQueue(compareBy { it.time })

    init {
        // 예시 작업 등록
        taskQueue.add(ScheduledTask(LocalTime.of(20, 16), { println("작업 1 실행: 오후 1시 59분") }))
        taskQueue.add(ScheduledTask(LocalTime.of(20, 15), { println("작업 2 실행: 오후 2시") }))
        taskQueue.add(ScheduledTask(LocalTime.of(20, 14), { println("작업 3 실행: 오후 2시 29분") }))
        taskQueue.add(ScheduledTask(LocalTime.of(20, 14), { println("작업 4 실행: 오후 2시 30분") }))
    }

//    @Scheduled(fixedDelay = 10 * 60 * 1000) // 1초마다 큐를 검사
    fun checkQueue() {
        while (taskQueue.isNotEmpty() && taskQueue.peek().time.isBefore(LocalTime.now().plusSeconds(1))) {
            taskQueue.poll().run()
        }
    }

    fun findNearestNext30Minute(currentTime: LocalTime): LocalTime {
        val minutesUntilNext30 = 30 - (currentTime.minute % 30)
        val nextHour = if (minutesUntilNext30 == 0) currentTime.hour + 1 else currentTime.hour
        val nextMinute = if (minutesUntilNext30 == 0) 0 else minutesUntilNext30
        return LocalTime.of(nextHour, nextMinute)
    }
}

data class ScheduledTask(val time: LocalTime, val task: Runnable) {
    fun run() {
        task.run()
    }
}
