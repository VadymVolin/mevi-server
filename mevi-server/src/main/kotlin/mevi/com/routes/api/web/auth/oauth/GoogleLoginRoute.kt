package mevi.com.routes.api.web.auth.oauth

import com.google.gson.annotations.SerializedName
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.sessions.*
import mevi.com.configs.applicationHttpClient
import mevi.com.constants.BEARER_HEADER_SEGMENT
//import mevi.com.configs.redirects
import mevi.com.constants.GOOGLE_AUTH_PROVIDER_ROUTE
import mevi.com.constants.GOOGLE_CLOUD_OAUTH2_USER_INFO_URL
import mevi.com.constants.GOOGLE_OAUTH_AUTH_ROUTE

data class UserSession(val state: String, val token: String)

data class UserInfo(
    val id: String,
    val email: String,
    @SerializedName("verified_email") val verifiedEmail: String,
    val name: String,
    @SerializedName("given_name") val givenName: String,
    @SerializedName("family_name") val familyName: String,
    val picture: String,
    val locale: String
)

fun Route.googleLoginRoute() {
    get(GOOGLE_OAUTH_AUTH_ROUTE) {
        call.application.environment.log.debug("FORTRA handle($GOOGLE_OAUTH_AUTH_ROUTE)")
    }

    get(GOOGLE_AUTH_PROVIDER_ROUTE) {
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
        val response = applicationHttpClient.get(call.application.environment.config.property(GOOGLE_CLOUD_OAUTH2_USER_INFO_URL).getString()) {
            headers {
                append(HttpHeaders.Authorization, "Bearer ${principal?.accessToken}")
            }
        }
        if (!response.status.isSuccess()) {
            call.respondText("Error: ${response.status.value} | ${response.status.description} | ${response.body<String>()}")
        }
        val userInfo: UserInfo = response.body()
        call.respondText("Hello, ${userInfo}!")
    }
}