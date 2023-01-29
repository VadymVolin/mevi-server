package mevi.com.constants

//auth
const val JWT_AUTH_SCOPE = "auth-jwt"
const val OAUTH_AUTH_SCOPE = "auth-oauth-google"
const val FIRSTNAME_PAYLAOD = "firstname"
const val LASTNAME_PAYLAOD = "lastname"
const val PHONE_NUMBER_PAYLAOD = "phonenumber"
const val AGE_PAYLAOD = "age"
const val USERNAME_PAYLOAD = "username"
const val PASSWORD_PAYLOAD = "password"
const val TOKEN = "token"

//JWT - RFC 7519: https://www.rfc-editor.org/rfc/rfc7519
const val JWT_SECRET = "jwt.secret"
const val JWT_ISS = "jwt.issuer"
const val JWT_AUD = "jwt.audience"
const val JWT_RLM = "jwt.realm"

//Google related keys
const val GOOGLE_PROVIDER_NAME = "google"
const val GOOGLE_CLOUD_CLIENT_SECRET = "google_cloud.client_secret"
const val GOOGLE_CLOUD_CLIENT_ID = "google_cloud.client_id"
const val GOOGLE_CLOUD_OAUTH2_AUTHORIZE_URL = "google_cloud.oauth2_authorize_url"
const val GOOGLE_CLOUD_OAUTH2_ACCESS_TOKEN_URL = "google_cloud.oauth2_access_token_url"
const val GOOGLE_CLOUD_OAUTH2_PROFILE_SCOPE = "google_cloud.oauth2_profile_scope"
const val GOOGLE_CLOUD_OAUTH2_EMAIL_SCOPE = "google_cloud.oauth2_email_scope"
const val GOOGLE_CLOUD_OAUTH2_USER_INFO_URL = "google_cloud.oauth2_user_info_url"

// Headers
const val ACCESS_TYPE = "access_type"
const val OFFLINE_ACCESS_TYPE = "offline"
const val BEARER_HEADER_SEGMENT = "Bearer"