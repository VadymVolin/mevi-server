package mevi.com.routes.api.web.auth.jwt

import com.auth0.jwt.algorithms.Algorithm.HMAC256
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mevi.com.constants.*
import mevi.com.routes.api.web.auth.jwt.models.EmailLoginUser
import java.util.*

fun Route.phoneLoginRoute() {
    post(JWT_PHONE_AUTH_ROUTE) {
        with(this.application.environment) {
            val secret = config.property(JWT_SECRET).getString()
            val issuer = config.property(JWT_ISS).getString()
            val audience = config.property(JWT_AUD).getString()
            val emailLoginUser = call.receive<EmailLoginUser>()
            // Check username and password
            val token = com.auth0.jwt.JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim(USERNAME_PAYLOAD, emailLoginUser.username)
                .withClaim(PASSWORD_PAYLOAD, emailLoginUser.password)
                .withIssuedAt(Date(System.currentTimeMillis()))
                .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                .sign(HMAC256(secret))
            call.respond(hashMapOf(TOKEN to token))
        }
    }
}