package no.nav.helse

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory
import io.ktor.server.cio.*

class App {
    private var webserver: NettyApplicationEngine? = null
    private val port = 8080

    fun configAndStartWebserver() {
        embeddedServer(CIO, port = 8080 ) {
            routing {
                get("/isalive") { call.respondText("ALIVE!") }
                get("/isready") { call.respondText("READY!") }
            }
        }.start(wait = true)
    }
}

fun main() {
    val logger = LoggerFactory.getLogger("spommer")
    logger.info("Hello spommer!")
    val app = App()
    app.configAndStartWebserver()
}