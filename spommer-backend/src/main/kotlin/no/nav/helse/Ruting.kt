import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.cio.*
import io.ktor.server.engine.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.http.content.*
internal fun configAndStartWebserver(): ApplicationEngine =
    embeddedServer(CIO, port = 8080 ) {
        routing {
            singlePageApplication {
                react("spommer-frontend/dist")
            }
            get("/isalive") { call.respondText("ALIVE!") }
            get("/isready") { call.respondText("READY!") }
        }
    }
