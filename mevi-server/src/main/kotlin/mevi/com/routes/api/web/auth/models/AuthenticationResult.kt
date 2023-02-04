package mevi.com.routes.api.web.auth.models

enum class AuthenticationResult(message: String) {
    //fail
    FAIL_ACCOUNT_DOES_NOT_EXIST("Account doesn't exist."),
    FAIL_ACCOUNT_IS_BLOCKED("Account is blocked."),
    FAIL_EMAIL_IS_ALREADY_REGISTERED("Email is already registered."),

    //success
    SUCCESS("Login successful.");
}