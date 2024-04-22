package net.bk24.macro.worker.infrastructure.webclient.helper

import org.springframework.web.client.RestTemplate

object NetworkHelper {
    fun getLocalIpAddress(): String? {
        val restTemplate = RestTemplate()
        val url = "http://checkip.amazonaws.com"
        return restTemplate.getForObject(url, String::class.java)?.trim()
    }
}
