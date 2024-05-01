package net.bk24.macro.worker.application.writer

import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

object VirtualThreadManager {
    @Volatile
    private var currentExecutor: ExecutorService? = null

    val executorService: ExecutorService
        get() {
            return currentExecutor ?: synchronized(this) {
                val instance = currentExecutor ?: Executors.newThreadPerTaskExecutor(Thread.ofVirtual().factory())
                currentExecutor = instance
                instance
            }
        }

    fun shutdownAndReset() {
        currentExecutor?.shutdownNow()
        currentExecutor = null
    }
}
