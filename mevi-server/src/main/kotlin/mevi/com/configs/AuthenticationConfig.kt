package mevi.com.config

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*

fun Application.configureAuthentication() {
    with(this@configureAuthentication.environment) {
        install(Authentication) {
            val secret = config.property(JWT_SECRET).getString()
            val issuer = config.property(JWT_ISS).getString()
            val audience = config.property(JWT_AUD).getString()
            val realmConfig = config.property(JWT_RLM).getString()
            jwt(AUTH_SCOPE) {
                realm = realmConfig
                verifier(
                    JWT.require(Algorithm.HMAC256(secret)).withAudience(audience).withIssuer(issuer).build()
                )
                validate { credential ->
                    if (credential.payload.getClaim(USERNAME_PAYLOAD)?.asString()?.isNotBlank() == true
                        && credential.payload.getClaim(PASSWORD_PAYLOAD)?.asString()?.isNotBlank() == true) {
                        JWTPrincipal(credential.payload)
                    } else {
                        null
                    }
                }
                challenge { defaultScheme, realm ->
                    call.respond(HttpStatusCode.Unauthorized, TOKEN_INVALID)
                }
            }
        }
    }
}