package ua.nure.smartlight.repository.group.dto

import kotlinx.serialization.Serializable

@Serializable
data class CreateGroupRequest(
    val groupId: Long? = null,
    val name: String? = null,
    val description: String? = null,
)