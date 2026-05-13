package ua.nure.smartlight.repository.lamp.dto

import kotlinx.serialization.Serializable

@Serializable
data class LampDto(
    val lampId: Long? = null,
    val name: String? = null,
    val groupId: Long,
    val description: String? = null,
    val r: Int? = null,
    val g: Int? = null,
    val b: Int? = null,
    val brightness: Int? = null,
    val active: Boolean = false,
)