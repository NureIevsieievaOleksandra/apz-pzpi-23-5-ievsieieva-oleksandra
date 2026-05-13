package ua.nure.smartlightadmin.repository.group.dto

import kotlinx.serialization.Serializable

@Serializable
data class GroupDto(
    val groupId: Long? = null,
    val name: String? = null,
    val description: String? = null,
) {
    companion object {
        val groupPreview = listOf(
            GroupDto(
                groupId = 1L,
                name = "Hall",
                description = "Hall name"
            ),
            GroupDto(
                groupId = 2L,
                name = "Yellow Room",
                description = "yellow"
            ),
            GroupDto(
                groupId = 3L,
                name = "Red Room",
                description = "Red"
            )
        )
    }
}
