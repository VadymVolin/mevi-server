package mevi.com.routes.api.web.auth.jwt

import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mevi.com.constants.JWT_FORM_AUTH_ROUTE
import mevi.com.constants.TOKEN
import mevi.com.handlers.AuthDataHandler
import mevi.com.routes.api.web.auth.models.EmailLoginUser

fun Route.emailLoginRoute() {
    post(JWT_FORM_AUTH_ROUTE) {
        with(this.application.environment) {
            val emailLoginUser = call.receive<EmailLoginUser>()
            val token = AuthDataHandler.createJwtToken(emailLoginUser.username, emailLoginUser.password)
            call.respond(hashMapOf(TOKEN to token))
        }
    }
}