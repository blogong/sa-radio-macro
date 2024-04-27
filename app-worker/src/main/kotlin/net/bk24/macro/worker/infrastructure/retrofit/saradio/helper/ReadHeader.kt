package net.bk24.macro.worker.infrastructure.retrofit.saradio.helper

import org.yaml.snakeyaml.Yaml
import java.io.InputStream

object ReadHeader {

    fun fromYml(): Headers {
        val fileName = "client.yml"

        val yaml = Yaml()
        val inputStream: InputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
            ?: throw IllegalArgumentException("File not found: $fileName")
        val data: Map<String, Any> = yaml.load(inputStream)

        val httpMap = data["http"] as Map<String, Any>
        val headersMap = httpMap["headers"] as Map<String, Any>

        return Headers(
            accept = headersMap["accept"] as String,
            connection = headersMap["connection"] as String,
            acceptEncoding = headersMap["acceptEncoding"] as String,
            contentType = headersMap["contentType"] as String,
            userAgent = headersMap["userAgent"] as String,
            host = headersMap["host"] as String,
            acceptLanguage = headersMap["acceptLanguage"] as String,
            cookie = headersMap["cookie"] as String,
        )
    }
}
