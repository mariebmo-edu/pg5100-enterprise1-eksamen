package no.kristiania.eksamen

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class EksamenApplication

fun main(args: Array<String>) {
    runApplication<EksamenApplication>(*args)
}
