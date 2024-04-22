package net.bk24.macro.worker

import io.sentry.Sentry
import net.bk24.macro.worker.infrastructure.discord.Bot
import net.bk24.macro.worker.infrastructure.webclient.helper.NetworkHelper
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

val localIpAddress = NetworkHelper.getLocalIpAddress()

@SpringBootApplication
class WorkerApplication {
    init {
        Sentry.captureMessage("[SERVER IP: $localIpAddress] 서버 실행 합니다.")
        Bot.send("[SERVER IP: $localIpAddress] 서버 실행 합니다.")
    }
}

fun main(args: Array<String>) {
    runApplication<WorkerApplication>(*args)
}
