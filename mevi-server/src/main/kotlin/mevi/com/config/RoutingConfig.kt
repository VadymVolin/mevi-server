package mevi.com.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mevi.com.models.User
import java.util.*

fun Application.configureRouting() {

    routing {
        post(AUTH_ROUTE) {
            with(this@configureRouting.environment) {
                val secret = config.property(JWT_SECRET).getString()
                val issuer = config.property(JWT_ISS).getString()
                val audience = config.property(JWT_AUD).getString()
                val user = call.receive<User>()
                // Check username and password
                val token = JWT.create()
                    .withAudience(audience)
                    .withIssuer(issuer)
                    .withClaim(USERNAME_PAYLOAD, user.username)
                    .withClaim(PASSWORD_PAYLOAD, user.password)
                    .withIssuedAt(Date(System.currentTimeMillis()))
                    .withExpiresAt(Date(System.currentTimeMillis() + 60000))
                    .sign(Algorithm.HMAC256(secret))
                call.respond(hashMapOf(TOKEN to token))
            }
        }
        authenticate(AUTH_SCOPE) {
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
    }
}