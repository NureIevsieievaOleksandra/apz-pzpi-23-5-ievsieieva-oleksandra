package ua.nure.smartlight.repository

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiErrorResponse(
    val message: String,
)

@Serializable
data class AttributeError(
    val attribute: String,
    val message: String,
)