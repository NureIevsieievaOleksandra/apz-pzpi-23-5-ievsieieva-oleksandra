package ua.nure.smartlight.repository.lamp.mapper

import ua.nure.smartlight.db.entity.LampEntity
import ua.nure.smartlight.repository.lamp.dto.LampDto

fun LampEntity.toDto() =
    LampDto(
        lampId = if(lampId > 0) lampId else null,
        name = name,
        groupId = groupId,
        description = description,
        r = r,
        g = g,
        b = b,
        brightness = brightness,
        active = active
    )