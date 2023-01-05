package mevi.com

import io.ktor.serialization.gson.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import mevi.com.config.configureAuthentication
import mevi.com.config.configureContentNegotiation
import mevi.com.config.configureCors
import mevi.com.config.configureRouting

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    configureCors()
    configureContentNegotiation()
    configureAuthentication()
    configureRouting()
}
