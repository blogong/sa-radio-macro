package net.bk24.macro.infra.clients

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties

@EnableConfigurationProperties(HttpHeaderProperties::class)
@ConfigurationProperties(prefix = "http.headers")
data class HttpHeaderProperties(
     val accept: String,
     val connection: String,
     val acceptEncoding: String,
     val contentType: String,
     val userAgent: String,
     val host: String,
     val acceptLanguage: String,
     val cookie: String,
)