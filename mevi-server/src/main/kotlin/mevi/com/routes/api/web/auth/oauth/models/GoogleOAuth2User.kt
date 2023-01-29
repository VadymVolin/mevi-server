package mevi.com.routes.api.web.auth.oauth.models

import com.google.gson.annotations.SerializedName

class GoogleOAuth2User(
    val id: String,
    val email: String,
    @SerializedName("verified_email") val verifiedEmail: String,
    val name: String,
    @SerializedName("given_name") val givenName: String,
    @SerializedName("family_name") val familyName: String,
    val picture: String,
    val locale: String
)