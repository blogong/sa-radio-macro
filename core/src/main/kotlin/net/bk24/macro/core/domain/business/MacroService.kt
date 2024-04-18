package net.bk24.macro.core.domain.business

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.springframework.stereotype.Component

@Component
class MacroService(
    private val popularPostMacroScheduler: PopularPostMacroScheduler,
    private val logSender: LogSender,
) {
    suspend fun startPopularPostMacro() {
        coroutineScope {
            async {
                popularPostMacroScheduler.scheduleJobs()
            }.await()
        }
    }
}
