package mevi.com.configs

import io.ktor.server.application.*

fun Application.initializeConfigurations() {
    configureDatabase(this)
    configureCors()
    configureContentNegotiation()
    configureAuthentication(applicationHttpClient)
    configureRouting()
}