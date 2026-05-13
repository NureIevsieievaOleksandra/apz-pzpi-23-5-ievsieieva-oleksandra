package ua.nure.smartlightadmin.repository.user

import ua.nure.smartlightadmin.repository.DataError
import ua.nure.smartlightadmin.repository.Result
import ua.nure.smartlightadmin.repository.auth.dto.Role
import ua.nure.smartlightadmin.repository.user.dto.UserDto

interface UserRepository {
    suspend fun list(): Result<List<UserDto>, DataError>
    suspend fun delete(userId: Long): Result<Any, DataError>
    suspend fun edit(userId: Long, role: Role): Result<UserDto, DataError>
}