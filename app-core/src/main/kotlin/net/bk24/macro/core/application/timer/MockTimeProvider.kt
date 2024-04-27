package net.bk24.macro.core.application.timer

import net.bk24.macro.common.Write
import java.util.Calendar
import java.util.Date
import java.util.TimeZone

object MockTimeProvider : TimeProvider {
    override fun isTimeToRun(currentTime: Date): Write {
        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"))
        calendar.time = currentTime

        val currentMinute = calendar.get(Calendar.MINUTE)

        if (currentMinute == 8) {
            return Write.COMMENT
        }
        return Write.POST
    }
}
