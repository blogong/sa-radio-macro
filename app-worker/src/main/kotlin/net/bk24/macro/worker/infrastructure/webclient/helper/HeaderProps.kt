package net.bk24.macro.worker.infrastructure.webclient.helper

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "http.headers.posts")
class HttpHeaderPostProperties(
    var accept: String = "",
    var connection: String = "",
    var acceptEncoding: String = "",
    var contentType: String = "",
    var userAgent: String = "",
    var host: String = "",
    var acceptLanguage: String = "",
    var cookie: String = "",
    var contentLength: String = "",
)

@ConfigurationProperties(prefix = "http.headers.comments")
class HttpHeaderCommentProperties(
    var accept: String = "",
    var connection: String = "",
    var acceptEncoding: String = "",
    var contentType: String = "",
    var userAgent: String = "",
    var host: String = "",
    var acceptLanguage: String = "",
    var cookie: String = "",
    var contentLength: String = "",
)
