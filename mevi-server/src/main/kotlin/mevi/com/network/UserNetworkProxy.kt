package mevi.com.network

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import mevi.com.constants.BEARER_HEADER_SEGMENT
import mevi.com.constants.GOOGLE_CLOUD_OAUTH2_USER_INFO_URL

object UserNetworkProxy {

    var application: Application? = null
    var applicationHttpClient: HttpClient? = null

    fun configure(application: Application, applicationHttpClient: HttpClient) {
        UserNetworkProxy.application = application
        UserNetworkProxy.applicationHttpClient = applicationHttpClient
    }

    fun getGoogleUserInfo(token: String): HttpResponse? {
        val httpClient = applicationHttpClient ?: return null
        val app = application ?: return null
        return runBlocking {
            return@runBlocking async {
                httpClient.get(app.environment.config.property(GOOGLE_CLOUD_OAUTH2_USER_INFO_URL).getString()) {
                    headers {
                        append(HttpHeaders.Authorization, "$BEARER_HEADER_SEGMENT $token")
                    }
                }
            }.await()
        }
    }

}