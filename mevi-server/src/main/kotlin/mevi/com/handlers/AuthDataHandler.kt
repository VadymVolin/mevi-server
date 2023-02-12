package mevi.com.handlers

import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.call.*
import io.ktor.http.*
import io.ktor.server.application.*
import mevi.com.cache.dao.UserDbProxy
import mevi.com.cache.dao.models.UserEntity
import mevi.com.constants.*
import mevi.com.handlers.models.Result
import mevi.com.network.UserNetworkProxy
import mevi.com.routes.api.web.auth.models.GoogleOAuth2User
import java.util.*

object AuthDataHandler {

    var application: Application? = null

    fun configure(application: Application) {
        AuthDataHandler.application = application
    }

    suspend fun authUserWithGoogle(oAuth2AccessToken: String?): Result<String> {
        val token = oAuth2AccessToken ?: return Result.Error(message = GENERIC_ERROR)
        val response = UserNetworkProxy.getGoogleUserInfo(token)
        if (response?.status?.isSuccess() != true) {
            return Result.Error(message = GENERIC_ERROR)
        }
        val userInfo: GoogleOAuth2User = response.body()

        val user: UserEntity = UserDbProxy.findUserByEmail(userInfo.email) ?: UserDbProxy.createGoogleUser(userInfo)
        return createJwtToken(user.email, user.password ?: user.provider)?.let {
            Result.Success(it)
        } ?: Result.Error(message = GENERIC_ERROR)
    }

    fun createJwtToken(usernameClaim: String?, passwordClaim: String?): String? {
        val app = application ?: return null
        with(app.environment) {
            val secret = config.property(JWT_SECRET).getString()
            val issuer = config.property(JWT_ISS).getString()
            val audience = config.property(JWT_AUD).getString()
            return com.auth0.jwt.JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim(USERNAME_PAYLOAD, usernameClaim)
                .withClaim(PASSWORD_PAYLOAD, passwordClaim)
                .withIssuedAt(Date(System.currentTimeMillis()))
                .withExpiresAt(Date(System.currentTimeMillis() + 240_000))
                .sign(Algorithm.HMAC256(secret))
        }
    }

}