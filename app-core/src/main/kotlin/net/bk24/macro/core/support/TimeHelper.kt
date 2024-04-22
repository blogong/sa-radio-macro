package net.bk24.macro.core.support

import java.time.LocalDateTime
import java.time.ZoneId

object TimeHelper {
    fun getNextMinuteMark(targetMinute: Int): LocalDateTime {
        val seoulZone = ZoneId.of("Asia/Seoul")
        var currentTime = LocalDateTime.now(seoulZone)

        // 현재 분이 목표 분을 넘었으면, 다음 시간으로 넘어감
        if (currentTime.minute >= targetMinute) {
            currentTime = currentTime.plusHours(1)
        }
        currentTime = currentTime.withMinute(targetMinute).withSecond(0).withNano(0)

        // 목표 분이 00분일 경우, 이미 시간을 넘겼으므로 다시 조정
        if (targetMinute == 0) {
            currentTime = currentTime.minusMinutes(60)
        }

        return currentTime
    }
}
