package mevi.com.routes.api.web.auth.oauth

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import mevi.com.configs.applicationHttpClient
import mevi.com.configs.redirects
import mevi.com.constants.GOOGLE_AUTH_RESPONSE_ROUTE
import mevi.com.constants.GOOGLE_OAUTH_AUTH_ROUTE
import kotlin.math.log

data class UserSession(val state: String, val token: String)
@Serializable
data class UserInfo(
    val id: String,
    val name: String,
    @SerialName("given_name") val givenName: String,
    @SerialName("family_name") val familyName: String,
    val picture: String,
    val locale: String
)

fun Route.googleLoginRoute() {
    get(GOOGLE_OAUTH_AUTH_ROUTE) {
        call.application.environment.log.debug("FORTRA handle: $GOOGLE_OAUTH_AUTH_ROUTE")
    }

    // TODO: check
    get(GOOGLE_AUTH_RESPONSE_ROUTE) {
//        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
//        call.sessions.set(UserSession(principal!!.state!!, principal.accessToken))
//        val redirect = redirects[principal.state!!]
//        call.respondRedirect(redirect!!)
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
        call.application.environment.log.debug("FORTRA accessToken: ${principal?.accessToken}")
        call.application.environment.log.debug("FORTRA state: ${principal?.state}")
        call.application.environment.log.debug("FORTRA refreshToken: ${principal?.refreshToken}")
        call.application.environment.log.debug("FORTRA expiresIn: ${principal?.expiresIn}")
        call.application.environment.log.debug("FORTRA tokenType: ${principal?.tokenType}")

        val userSession: UserSession = UserSession(principal!!.state!!, principal.accessToken)
        call.application.environment.log.debug("FORTRA : " + applicationHttpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
            }
        }.body<Any?>().toString())
        val userInfo: UserInfo = applicationHttpClient.get("https://www.googleapis.com/oauth2/v2/userinfo") {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${userSession.token}")
            }
        }.body()
        call.respondText("Hello, ${userInfo}!")
    }
}