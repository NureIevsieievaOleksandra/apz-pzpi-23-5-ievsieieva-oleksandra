package ua.nure.smartlight.repository.auth.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = RoleSerializer::class)
enum class Role {
    Admin, User, Undefined
}

object RoleSerializer : KSerializer<Role> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Role", kind = PrimitiveKind.INT)

    override fun serialize(
        encoder: Encoder,
        value: Role
    ) {
        encoder.encodeInt(
            when(value) {
                Role.Admin -> 0
                Role.User -> 1
                Role.Undefined -> 2
            }
        )
    }

    override fun deserialize(decoder: Decoder): Role =
        when(val result = decoder.decodeInt()) {
            0 -> Role.Admin
            1 -> Role.User
            2 -> Role.Undefined
            else -> throw IllegalArgumentException("Unknown Role: $result")
        }

}