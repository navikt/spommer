package no.nav.helse

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.slf4j.LoggerFactory

class App {
    private var webserver: NettyApplicationEngine? = null
    private val port = 8080

    fun configAndStartWebserver() {
        webserver = embeddedServer(
            Netty,
            applicationEngineEnvironment {
                connector {
                    port = this@App.port
                }

                module {
                    routing {
                        get("/is-alive") {
                            call.respondText("ALIVE", ContentType.Text.Plain)
                        }

                        get("/is-ready") {
                            call.respondText("READY", ContentType.Text.Plain)
                        }
                    }
                }
            }
        )

        webserver!!.start(wait = false)
    }

    fun shutdown() {
        webserver!!.stop(1000, 1000)
    }
}

fun main() {
    val logger = LoggerFactory.getLogger("spommer")
    logger.info("Hello spommer!")
    val app = App()
    app.configAndStartWebserver()

    Runtime.getRuntime().addShutdownHook(
        Thread {
            logger.info("Fikk shutdown-signal, avslutter...")
            app.shutdown()
            logger.info("Avsluttet OK")
        }
    )
}