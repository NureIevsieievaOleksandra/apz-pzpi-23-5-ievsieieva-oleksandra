package ua.nure.data.admin.dto

import kotlinx.serialization.Serializable

@Serializable
data class BackupRequest(
    val fileName: String,
)
