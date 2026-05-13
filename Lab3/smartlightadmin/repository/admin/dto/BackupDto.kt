package ua.nure.data.admin

import kotlinx.serialization.Serializable

@Serializable
data class BackupDto(
    val fileName: String,
    val size: Long,
    val modified: Long,
) {
    companion object {
        val backupPreview = BackupDto(
            fileName = "backup_light_20260505_193922.dump",
            size = 45980,
            modified = 1777999162918
        )
    }
}