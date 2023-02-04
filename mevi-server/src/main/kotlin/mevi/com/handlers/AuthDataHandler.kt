package mevi.com.handlers

import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import mevi.com.configs.applicationHttpClient
import mevi.com.constants.*
import mevi.com.routes.api.web.auth.models.GoogleOAuth2User
import java.util.*

object AuthDataHandler {

    var application: Application? = null

    fun configure(application: Application) {
        AuthDataHandler.application = application
    }

    fun authUserWithGoogle(oAuth2AccessToken: String?) {
        val app = application ?: return null
        val token = oAuth2AccessToken ?: return null
        val response = applicationHttpClient.get(app.environment.config.property(GOOGLE_CLOUD_OAUTH2_USER_INFO_URL).getString()) {
            headers {
                append(HttpHeaders.Authorization, "$BEARER_HEADER_SEGMENT $oAuth2AccessToken")
            }
        }
        if (!response.status.isSuccess()) {
            app.respondText("Error: ${response.status.value} | ${response.status.description} | ${response.body<String>()}")
        }
        val userInfo: GoogleOAuth2User = response.body()
    }

    fun createJwtToken(usernameClaim: String, passwordClaim: String): String? {
        val app = application ?: return null
        with(app.environment) {
            val secret = config.property(JWT_SECRET).getString()
            val issuer = config.property(JWT_ISS).getString()
            val audience = config.property(JWT_AUD).getString()
            return com.auth0.jwt.JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim(USERNAME_PAYLOAD, usernameClaim)
                .withClaim(PASSWORD_PAYLOAD, passwordClaim)
                .withIssuedAt(Date(System.currentTimeMillis()))
                .withExpiresAt(Date(System.currentTimeMillis() + 240_000))
                .sign(Algorithm.HMAC256(secret))
        }
    }

}