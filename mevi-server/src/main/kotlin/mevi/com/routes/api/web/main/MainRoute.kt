package mevi.com.routes.api.web.main

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mevi.com.constants.ROOT_ROUTE
import mevi.com.constants.TOKEN_INVALID
import mevi.com.constants.USERNAME_PAYLOAD

fun Route.mainRoute() {
    get(ROOT_ROUTE) {
        val principal = call.principal<JWTPrincipal>() ?: run {
            call.respond(HttpStatusCode.Unauthorized, TOKEN_INVALID)
            return@get
        }
        val username = principal.payload.getClaim(USERNAME_PAYLOAD).asString()
        val expiresAt = principal.expiresAt?.time?.minus(System.currentTimeMillis())
        call.respondText("Hello, $username! Token is expired at $expiresAt ms.")
    }
}