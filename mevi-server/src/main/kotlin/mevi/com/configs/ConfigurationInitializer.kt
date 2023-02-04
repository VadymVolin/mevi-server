package mevi.com.configs

import io.ktor.server.application.*

fun Application.initializeConfigurations() {
    configureCors()
    configureContentNegotiation()
    configureDatabase(this)
    configureHandlers(this)
    configureAuthentication(applicationHttpClient)
    configureRouting()
}