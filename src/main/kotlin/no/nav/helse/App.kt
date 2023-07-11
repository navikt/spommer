package no.nav.helse

import Konfig
import com.zaxxer.hikari.HikariDataSource
import configAndStartWebserver
import org.slf4j.LoggerFactory
import kotlinx.coroutines.runBlocking

fun main() {
    val logger = LoggerFactory.getLogger("spommer")
    logger.info("Hello spommer!")

    val konfig = Konfig.fromEnv()
    val database = DataSourceBuilder(konfig).getDataSource()
    val app = App(database)
    app.startBlocking()
}

internal class App(
    private val database: HikariDataSource
) {

    fun startBlocking() {
        runBlocking {
            configAndStartWebserver().start(wait = false)
            Runtime.getRuntime().addShutdownHook(
                Thread{
                    database.close()
                }
            )
        }
    }
}
