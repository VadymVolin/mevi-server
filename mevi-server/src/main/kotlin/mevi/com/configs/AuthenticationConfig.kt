package mevi.com.configs

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.response.*
import io.ktor.util.*
import mevi.com.constants.*

//val redirects = mutableMapOf<String, String>()

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
                        && credential.payload.getClaim(PASSWORD_PAYLOAD)?.asString()?.isNotBlank() == true
                    ) {
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
            oauth(OAUTH_AUTH_SCOPE) {
                urlProvider = { BASE_URL.plus(GOOGLE_AUTH_PROVIDER_ROUTE) }
                providerLookup = {
                    OAuthServerSettings.OAuth2ServerSettings(
                        name = GOOGLE_PROVIDER_NAME,
                        authorizeUrl = config.property(GOOGLE_CLOUD_OAUTH2_AUTHORIZE_URL).getString(),
                        accessTokenUrl = config.property(GOOGLE_CLOUD_OAUTH2_ACCESS_TOKEN_URL).getString(),
                        requestMethod = HttpMethod.Post,
                        clientId = config.property(GOOGLE_CLOUD_CLIENT_ID).getString(),
                        clientSecret = config.property(GOOGLE_CLOUD_CLIENT_SECRET).getString(),
                        defaultScopes = listOf(
                            config.property(GOOGLE_CLOUD_OAUTH2_PROFILE_SCOPE).getString(),
                            config.property(GOOGLE_CLOUD_OAUTH2_EMAIL_SCOPE).getString()
                        ),
                        extraAuthParameters = listOf(ACCESS_TYPE to OFFLINE_ACCESS_TYPE),
                        onStateCreated = { call, state ->
//                            call.application.environment.log.debug("FORTRA oauth: $state")
//                            call.application.environment.log.debug("FORTRA oauth: ${call.request.queryParameters["redirectUrl"].toString()}")
//                            call.application.environment.log.debug(
//                                "FORTRA oauth: ${
//                                    call.request.rawQueryParameters.toMap().toString()
//                                }"
//                            )
//                            call.application.environment.log.debug("FORTRA oauth: ${call.response.status()}")
//
//                            call.request.queryParameters["redirectUrl"]?.let { redirects[state] = it }

                        }
                    )
                }
                client = httpClient

            }
        }
    }
}