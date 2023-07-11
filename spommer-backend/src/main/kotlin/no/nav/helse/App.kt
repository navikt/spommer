package no.nav.helse

import Konfig
import configAndStartWebserver
import org.slf4j.LoggerFactory
import kotlinx.coroutines.runBlocking

fun main() {
    val logger = LoggerFactory.getLogger("spommer")
    logger.info("Hello spommer!")

    val konfig = Konfig.fromEnv()
    val dataSourceBuilder = DataSourceBuilder(konfig)
    val app = App(dataSourceBuilder)
    app.startBlocking()
}

internal class App(
    private val database: DataSourceBuilder
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
