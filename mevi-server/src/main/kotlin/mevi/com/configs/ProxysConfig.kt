package mevi.com.configs

import io.ktor.client.*
import io.ktor.server.application.*
import mevi.com.network.UserNetworkProxy

fun configurePoxy(application: Application, applicationHttpClient: HttpClient) {
    UserNetworkProxy.configure(application, applicationHttpClient)
}