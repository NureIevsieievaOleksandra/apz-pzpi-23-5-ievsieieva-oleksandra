package ua.nure.smartlightadmin.repository.group

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.nure.smartlightadmin.repository.DataError
import ua.nure.smartlightadmin.repository.Result
import ua.nure.smartlightadmin.repository.group.dto.GroupDto
import ua.nure.smartlightadmin.repository.group.dto.UpdateGroupRequest
import ua.nure.smartlightadmin.repository.safeCall

class GroupRepositoryImpl(
    private val httpClient: HttpClient,
) : GroupRepository {
    override suspend fun list(): Result<List<GroupDto>, DataError> = withContext(Dispatchers.Default) {
        safeCall<List<GroupDto>> {
            httpClient.get("group")
        }
    }

    override suspend fun update(
        groupId: Long,
        name: String?,
        description: String?
    ): Result<GroupDto, DataError>  = withContext(Dispatchers.Default) {
        safeCall<GroupDto> {
            httpClient.put("group") {
                setBody(
                    UpdateGroupRequest(
                        groupId = groupId,
                        name = name,
                        description = description
                    )
                )
            }
        }
    }

    override suspend fun delete(groupId: Long): Result<Any, DataError> = withContext(Dispatchers.Default) {
        safeCall<Any> {
            httpClient.delete("group/$groupId")
        }
    }

    override suspend fun create(
        name: String,
        description: String
    ): Result<GroupDto, DataError>  = withContext(Dispatchers.Default) {
        safeCall<GroupDto> {
            httpClient.post("group") {
                setBody(
                    UpdateGroupRequest(
                        name = name,
                        description = description
                    )
                )
            }
        }
    }
}