package net.bk24.macro.core.application.timer

import net.bk24.macro.core.application.FamousMaker
import java.util.Date

interface TimeProvider {
    fun isTimeToRun(currentTime: Date): FamousMaker.Type?
}
