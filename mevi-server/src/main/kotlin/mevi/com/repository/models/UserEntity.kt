package mevi.com.repository.models

data class UserEntity(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    val bio: String,
    val email: String,
    val password: String,
    val country: String,
    val gender: String
)