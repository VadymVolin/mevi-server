package mevi.com.routes.api.web.auth.oauth

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mevi.com.configs.applicationHttpClient
import mevi.com.constants.BEARER_HEADER_SEGMENT
import mevi.com.constants.GOOGLE_AUTH_PROVIDER_ROUTE
import mevi.com.constants.GOOGLE_CLOUD_OAUTH2_USER_INFO_URL
import mevi.com.constants.GOOGLE_OAUTH_AUTH_ROUTE
import mevi.com.handlers.AuthDataHandler
import mevi.com.routes.api.web.auth.models.GoogleOAuth2User


fun Route.googleLoginRoute() {
    get(GOOGLE_OAUTH_AUTH_ROUTE) {
        //redirect to google login form
    }

    get(GOOGLE_AUTH_PROVIDER_ROUTE) {
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
        val token = AuthDataHandler.authUserWithGoogle(principal?.accessToken)

        // check is user exist
        // true? update
        // else create and proceed
        // generate jwt
        // respond

        call.respondText("Hello, ${token}!")
    }
}