package mevi.com.configs

import io.ktor.server.application.*

fun Application.initializeConfigurations() {
    configureCors()
    configureContentNegotiation()
    configureAuthentication(applicationHttpClient)
    configureRouting()
}