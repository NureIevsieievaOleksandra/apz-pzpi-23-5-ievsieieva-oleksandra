package ua.nure.smartlight.db.entity

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlinx.serialization.Serializable

@Entity
data class GroupEntity(
    @PrimaryKey val groupId: Long,
    val name: String? = null,
    val description: String? = null,
)

data class Group(
    @Embedded val group: GroupEntity,
    @Relation(
        entity = LampEntity::class,
        entityColumn = "groupId",
        parentColumn = "groupId",
    ) val lamps: List<LampEntity>
) {
    companion object {
        val groupPreview = listOf(
            Group(
                group = GroupEntity(
                    groupId = 5,
                    name = "Hall",
                    description = "The biggest room"
                ),
                lamps =  listOf(
                    LampEntity(
                        lampId = 1,
                        name = "Analytic test lamp",
                        groupId = 5,
                        description = null,
                        r = 128,
                        g = 124,
                        b = 145,
                        brightness = 255,
                        active = true
                    )
                )
            ),
            Group(
                group = GroupEntity(
                    groupId = 6,
                    name = "Bedroom",
                    description = "Bedroom"
                ),
                lamps =  emptyList()
                )
            )
    }
}
