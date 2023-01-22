package mevi.com.configs

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.serialization.gson.*
import io.ktor.client.plugins.contentnegotiation.*
import java.text.DateFormat

val applicationHttpClient = HttpClient(CIO) {
    install(ContentNegotiation) {
        gson {
            setDateFormat(DateFormat.LONG)
            setPrettyPrinting()
        }
    }
}