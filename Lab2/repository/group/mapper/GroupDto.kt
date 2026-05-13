package ua.nure.smartlight.repository.group.mapper

import ua.nure.smartlight.db.entity.GroupEntity
import ua.nure.smartlight.repository.group.dto.GroupDto

fun GroupDto.toEntity() =
    GroupEntity(
        groupId = groupId,
        name = name,
        description = description
    )