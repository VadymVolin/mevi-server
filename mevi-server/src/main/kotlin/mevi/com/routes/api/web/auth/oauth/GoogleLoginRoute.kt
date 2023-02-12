package mevi.com.routes.api.web.auth.oauth

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mevi.com.constants.GENERIC_ERROR
import mevi.com.constants.GOOGLE_AUTH_PROVIDER_ROUTE
import mevi.com.constants.GOOGLE_OAUTH_AUTH_ROUTE
import mevi.com.handlers.AuthDataHandler
import mevi.com.handlers.models.Result


fun Route.googleLoginRoute() {
    get(GOOGLE_OAUTH_AUTH_ROUTE) {
        //redirect to google login form
    }

    get(GOOGLE_AUTH_PROVIDER_ROUTE) {
        val principal: OAuthAccessTokenResponse.OAuth2? = call.principal()
        when (val result = AuthDataHandler.authUserWithGoogle(principal?.accessToken)) {
            is Result.Success -> call.respond(HttpStatusCode.OK, result.data ?: GENERIC_ERROR)
            is Result.Error -> call.respond(HttpStatusCode.ServiceUnavailable, result.message ?: GENERIC_ERROR)
        }
    }
}