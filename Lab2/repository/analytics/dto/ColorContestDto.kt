package ua.nure.smartlight.repository.analytics.dto

import kotlinx.serialization.Serializable

@Serializable
data class ColorContestDto(
    val r: Int? = null,
    val g: Int? = null,
    val b: Int? = null,
    val count: Long,
)
