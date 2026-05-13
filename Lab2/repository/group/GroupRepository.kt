package ua.nure.smartlight.repository.group

import kotlinx.coroutines.flow.Flow
import ua.nure.smartlight.db.entity.Group
import ua.nure.smartlight.db.entity.GroupEntity
import ua.nure.smartlight.repository.DataError
import ua.nure.smartlight.repository.Result
import ua.nure.smartlight.repository.group.dto.GroupDto

interface GroupRepository {
    suspend fun load()
    fun get(): Flow<List<Group>>
    suspend fun create(title: String, description: String): Result<GroupEntity, DataError>
    suspend fun edit(groupId: Long, title: String, description: String): Result<GroupEntity, DataError>
    suspend fun delete(groupId: Long): Result<Any, DataError>
}