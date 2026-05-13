package ua.nure.smartlightadmin.repository.auth.dto

import kotlinx.serialization.Serializable

@Serializable
data class SignInResponse(
    val token: String,
    val userId: Long?,
    val userName: String?,
    val role: Role
)
