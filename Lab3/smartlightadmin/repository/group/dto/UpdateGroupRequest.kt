package ua.nure.smartlightadmin.repository.group.dto

import kotlinx.serialization.Serializable

@Serializable
data class UpdateGroupRequest(
    val groupId: Long? = null,
    val name: String? = null,
    val description: String? = null,
)
