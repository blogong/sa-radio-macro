package net.bk24.macro.worker.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate

@RestController
class HealthController {

    @GetMapping("/")
    fun healthCheck(): String? {
        val restTemplate = RestTemplate()
        val url = "http://checkip.amazonaws.com"
        return restTemplate.getForObject(url, String::class.java)?.trim()
    }
}
