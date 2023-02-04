package mevi.com.routes.api.web.auth.jwt

import com.auth0.jwt.algorithms.Algorithm.HMAC256
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mevi.com.constants.*
import mevi.com.handlers.AuthDataHandler
import mevi.com.routes.api.web.auth.models.EmailLoginUser
import java.util.*

fun Route.emailLoginRoute() {
    post(JWT_FORM_AUTH_ROUTE) {
        with(this.application.environment) {
            val emailLoginUser = call.receive<EmailLoginUser>()
            val token = AuthDataHandler.createJwtToken(emailLoginUser.username, emailLoginUser.password)
            call.respond(hashMapOf(TOKEN to token))
        }
    }
}