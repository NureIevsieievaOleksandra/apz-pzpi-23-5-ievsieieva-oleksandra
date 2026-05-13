package ua.nure.smartlight.repository.group.dto

import kotlinx.serialization.Serializable
import ua.nure.smartlight.repository.lamp.dto.LampDto

@Serializable
data class GroupDto(
    val groupId: Long,
    val name: String? = null,
    val description: String? = null,
    val lamps: List<LampDto> = emptyList(),
)
