import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import no.nav.helse.BordDao

internal fun configAndStartWebserver(bordDao: BordDao): ApplicationEngine =
    embeddedServer(CIO, port = 8080 ) {
        routing {
            get("/isalive") { call.respondText("ALIVE!") }
            get("/isready") { call.respondText("READY!") }
        }
    }
