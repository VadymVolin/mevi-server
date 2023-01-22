package mevi.com.configs

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import mevi.com.constants.*


fun Application.configureAuthentication(httpClient: HttpClient) {
    with(this@configureAuthentication.environment) {
        install(Authentication) {
            // jwt
            val secret = config.property(JWT_SECRET).getString()
            val issuer = config.property(JWT_ISS).getString()
            val audience = config.property(JWT_AUD).getString()
            val realmConfig = config.property(JWT_RLM).getString()
            jwt(JWT_AUTH_SCOPE) {
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

            // oauth
            val redirects = mutableMapOf<String, String>()
            oauth(OAUTH_AUTH_SCOPE) {
                TODO("env settings")
//                val env = this.application.environment
                urlProvider = { BASE_URL.plus(GOOGLE_AUTH_RESPONSE_ROUTE) }
                providerLookup = {
                    OAuthServerSettings.OAuth2ServerSettings(
                        name = "google",
                        authorizeUrl = "https://accounts.google.com/o/oauth2/auth",
                        accessTokenUrl = "https://accounts.google.com/o/oauth2/token",
                        requestMethod = HttpMethod.Post,
                        clientId = System.getenv("GOOGLE_CLIENT_ID"),
                        clientSecret = System.getenv("GOOGLE_CLIENT_SECRET"),
                        defaultScopes = listOf("https://www.googleapis.com/auth/userinfo.profile"),
                        extraAuthParameters = listOf("access_type" to "offline"),
                        onStateCreated = { call, state ->
                            redirects[state] = call.request.queryParameters["redirectUrl"]!!
                        }
                    )
                }
                client = httpClient
            }
        }
    }
}