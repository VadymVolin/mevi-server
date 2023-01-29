package mevi.com.configs

import io.ktor.server.application.*
import mevi.com.configs.db.configureDatabase

fun Application.initializeConfigurations() {
    configureDatabase()
    configureCors()
    configureContentNegotiation()
    configureAuthentication(applicationHttpClient)
    configureRouting()
}