package net.bk24.macro.worker.infrastructure.retrofit

import org.springframework.web.client.RestTemplate

object GetLocalIpAddress {
    fun execute(): String {
        val restTemplate = RestTemplate()
        val url = "http://checkip.amazonaws.com"
        return restTemplate.getForObject(url, String::class.java)!!.trim()
    }
}
