package no.nav.helse

import org.slf4j.LoggerFactory
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.cio.*

class App {

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
/*
fun main() {
    val logger = LoggerFactory.getLogger("spommer")
    logger.info("Hello spommer!")

    //val konfig = Konfig.fromEnv()
    //val dataSourceBuilder = DataSourceBuilder(konfig)
    val app = App()
    app.startBlocking()
}

internal class App(
    //private val database: DataSourceBuilder
) {
    //private val bordDao = BordDao(database.getDataSource())

    fun startBlocking() {
        runBlocking {
            //database.migrate()
            start()
        }
    }

    fun start() {
        configAndStartWebserver().start(wait = false)
    }
}

 */
