package net.bk24.macro.core.infrastructure.client.webhook

import org.yaml.snakeyaml.Yaml
import java.io.InputStream

object GetWorkerIp {

    fun loadIpList(fileName: String = "client.yml"): List<String> {
        val yaml = Yaml()
        val inputStream: InputStream = Thread.currentThread().contextClassLoader.getResourceAsStream(fileName)
            ?: throw IllegalArgumentException("File not found: $fileName")

        val data = yaml.load<Map<String, Any>>(inputStream)
        return data["workers"]?.let {
            @Suppress("UNCHECKED_CAST")
            val workers = it as Map<String, Any>
            workers["ip"] as List<String>
        } ?: emptyList()
    }
}
