package ua.nure.smartlightadmin.repository.token

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ua.nure.smartlightadmin.repository.auth.dto.Role

interface TokenRepository {
    val token: String?
    val userName: String?
    val userNameFlow: StateFlow<UserName>
    val role: Role
    val userId: Long
    suspend fun setToken(newToken: String?)
    suspend fun setUserName(newUserName: String?)

    suspend fun setRole(role: Role)
    suspend fun setUserId(id: Long?)
}

typealias UserName = String?