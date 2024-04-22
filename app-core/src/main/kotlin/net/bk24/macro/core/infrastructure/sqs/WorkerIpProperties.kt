package net.bk24.macro.core.infrastructure.sqs

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "workers")
class WorkerIpProperties {
    var ip: List<String> = emptyList()
}
