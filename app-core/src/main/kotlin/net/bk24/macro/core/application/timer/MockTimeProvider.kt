package net.bk24.macro.core.application.timer

import net.bk24.macro.core.application.FamousMaker
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

object MockTimeProvider : TimeProvider {
    override fun isTimeToRun(currentTime: Date): FamousMaker.Type {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))
        calendar.time = currentTime

        val currentMinute = calendar.get(Calendar.MINUTE)

        if (currentMinute == 8) {
            return FamousMaker.Type.COMMENT
        }
        return FamousMaker.Type.POST
    }
}
