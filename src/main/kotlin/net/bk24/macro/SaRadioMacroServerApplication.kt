package net.bk24.macro

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SaRadioMacroServerApplication

fun main(args: Array<String>) {
    runApplication<SaRadioMacroServerApplication>(*args)
}
