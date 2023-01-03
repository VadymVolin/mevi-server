package mevi.com

import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*
import mevi.com.plugins.*

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(CORS)
    configureRouting()
}
