package net.bk24.macro.core.presentation.api

import net.bk24.macro.core.application.FamousMaker
import net.bk24.macro.core.application.writer.PostProvider
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import java.time.ZoneId
import java.time.ZonedDateTime

@RestController
class TaskApi(
    private val famousMaker: FamousMaker,
    private val postProvider: PostProvider,
) {
    @PostMapping("/sendTasks")
    fun sendTasks(): ResponseEntity<Void> {
        while (true) {
            // 한국 시간대로 현재 시각을 구함
            val now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

            // 현재 분이 29분인지 확인
            if (now.minute == 29) {
                // 29분일 때 실행할 코드
//                    postProvider.execute()
                Thread.sleep(30 * 1000) // 30분 후에 루프 시작
                famousMaker.execute()
            }
            // 1분마다 체크하도록 60초 대기
            Thread.sleep(60000)
        }
    }
}
