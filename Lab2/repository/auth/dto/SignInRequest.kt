package ua.nure.smartlight.repository.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignInRequest(
    val username: String,
    val password: String
)
