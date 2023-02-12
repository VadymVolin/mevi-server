package mevi.com.configs

import io.ktor.server.application.*
import mevi.com.handlers.AuthDataHandler

fun configureHandlers(application: Application) {
    AuthDataHandler.configure(application)
}