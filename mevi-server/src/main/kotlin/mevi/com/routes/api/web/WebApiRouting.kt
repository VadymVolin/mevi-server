    package mevi.com.routes.api.web

import io.ktor.server.auth.*
import io.ktor.server.routing.*
import mevi.com.constants.JWT_AUTH_SCOPE
import mevi.com.routes.api.web.auth.jwt.emailLoginRoute
import mevi.com.routes.api.web.auth.jwt.emailRegistrationRoute
import mevi.com.routes.api.web.auth.jwt.phoneLoginRoute
import mevi.com.routes.api.web.auth.jwt.phoneRegistrationRoute
import mevi.com.routes.api.web.auth.oauth.appleLoginRoute
import mevi.com.routes.api.web.auth.oauth.facebookLoginRoute
import mevi.com.routes.api.web.auth.oauth.googleLoginRoute
import mevi.com.routes.api.web.main.mainRoute

fun Routing.registerWebApiRoutes() {
    // sign up
    emailRegistrationRoute()
    phoneRegistrationRoute()

    // sign in
    emailLoginRoute()
    phoneLoginRoute()
    googleLoginRoute()
    facebookLoginRoute()
    appleLoginRoute()

    authenticate(JWT_AUTH_SCOPE) {
        mainRoute()
    }
}