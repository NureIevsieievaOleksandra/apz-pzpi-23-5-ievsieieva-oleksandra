package ua.nure.smartlightadmin.repository.group

import ua.nure.smartlightadmin.repository.DataError
import ua.nure.smartlightadmin.repository.Result
import ua.nure.smartlightadmin.repository.group.dto.GroupDto

interface GroupRepository {
    suspend fun list(): Result<List<GroupDto>, DataError>
    suspend fun update(
        groupId: Long,
        name: String? = null,
        description: String? = null,
    ): Result<GroupDto, DataError>

    suspend fun delete(
        groupId: Long
    ): Result<Any, DataError>

    suspend fun create(
        name: String,
        description: String
    ): Result<GroupDto, DataError>
}
