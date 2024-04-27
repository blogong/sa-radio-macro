package net.bk24.macro.core.application.timer

import net.bk24.macro.common.Write
import java.util.Date

interface TimeProvider {
    fun isTimeToRun(currentTime: Date): Write?
}
