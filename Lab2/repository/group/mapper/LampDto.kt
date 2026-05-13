package ua.nure.smartlight.repository.group.mapper

import ua.nure.smartlight.db.entity.LampEntity
import ua.nure.smartlight.repository.lamp.dto.LampDto

fun LampDto.toEntit() =
    LampEntity(
        lampId = lampId ?: -1L,
        name =name,
        groupId =groupId,
        description =description,
        r =r,
        g =g,
        b =b,
        brightness =brightness,
        active =active,
    )