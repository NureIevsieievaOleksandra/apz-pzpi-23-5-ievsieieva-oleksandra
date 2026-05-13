package ua.nure.smartlightadmin.repository.user

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.http.cio.parseHttpBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.nure.smartlightadmin.repository.DataError
import ua.nure.smartlightadmin.repository.Result
import ua.nure.smartlightadmin.repository.auth.dto.Role
import ua.nure.smartlightadmin.repository.safeCall
import ua.nure.smartlightadmin.repository.user.dto.EditUserRequest
import ua.nure.smartlightadmin.repository.user.dto.UserDto

class UserRepositoryImpl(
    private val httpClient: HttpClient,
) : UserRepository{
    override suspend fun list(): Result<List<UserDto>, DataError> = withContext(Dispatchers.Default){
        safeCall<List<UserDto>> {
            httpClient.get("user") {

            }
        }
    }

    override suspend fun delete(userId: Long): Result<Any, DataError> =
    withContext(Dispatchers.Default) {
        safeCall<Any> {
            httpClient.delete("user/$userId")
        }
    }

    override suspend fun edit(
        userId: Long,
        role: Role
    ): Result<UserDto, DataError>  = withContext(Dispatchers.Default) {
        safeCall {
            httpClient.put("user") {
                setBody(
                    EditUserRequest(
                        userId = userId,
                        role = role
                    )
                )
            }
        }
    }
}