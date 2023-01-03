package mevi.com.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureAuthentication() {
    val environment = this@configureAuthentication.environment
    install(Authentication) {
        val secret = environment.config.property("jwt.secret").getString()
        val issuer = environment.config.property("jwt.issuer").getString()
        val audience = environment.config.property("jwt.audience").getString()
        val realmConfig = environment.config.property("jwt.realm").getString()
        jwt("auth-jwt") {
            realm = realmConfig
            verifier(
                JWT.require(Algorithm.HMAC256(secret)).withAudience(audience).withIssuer(issuer).build()
            )
            validate { credential ->

                this@configureAuthentication.log.info("$credential")

                if (credential.payload.getClaim("username")?.asString()?.isNotBlank() == true) {
                    JWTPrincipal(credential.payload)
                } else {
                    null
                }
            }
            challenge { defaultScheme, realm ->
                call.respond(HttpStatusCode.Unauthorized, "Token is not valid or has expired")
            }
        }
    }
}