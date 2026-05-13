package ua.nure.smartlightadmin.repository.user.dto

import kotlinx.serialization.Serializable
import ua.nure.smartlightadmin.repository.auth.dto.Role

@Serializable
data class EditUserRequest(
    val userId: Long,
    val role: Role,
)
