package mevi.com.configs

import io.ktor.server.application.*
import io.ktor.server.routing.*
import mevi.com.routes.api.web.registerWebApiRoutes

fun Application.configureRouting() {
    routing {
        registerWebApiRoutes()
    }
}