package ua.nure.smartlightadmin.repository.user.dto

import kotlinx.serialization.Serializable
import ua.nure.smartlightadmin.repository.auth.dto.Role

@Serializable
data class UserDto(
    val userId: Long? = null,
    val name: String,
    val role: Role? = null,
) {
    companion object {
        val userPreview = listOf(
            UserDto(
                userId = 1,
                name = "Admin user",
                role = Role.Admin
            ),
            UserDto(
                userId = 2,
                name = "Night admin",
                role = Role.Admin
            ),
            UserDto(
                userId = 3,
                name = "User",
                role = Role.User
            ),
            UserDto(
                userId = 4,
                name = "john_doe",
                role = Role.User
            ),
            UserDto(
                userId = 5,
                name = "jane_doe",
                role = Role.User
            )
        )
    }
}
