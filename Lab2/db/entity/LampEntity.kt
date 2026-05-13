package ua.nure.smartlight.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class LampEntity(
    @PrimaryKey val lampId: Long,
    val name: String? = null,
    val groupId: Long,
    val description: String? = null,
    val r: Int? = null,
    val g: Int? = null,
    val b: Int? = null,
    val brightness: Int? = null,
    val active: Boolean = false,
)