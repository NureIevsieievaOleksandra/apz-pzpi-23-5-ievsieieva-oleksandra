package ua.nure.smartlight.repository.token

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import ua.nure.smartlight.repository.auth.dto.Role
import ua.nure.smartlight.ui.settings.compose.ThemeEntity

interface TokenRepository {
    val token: String?
    val userName: String?
    val userNameFlow: StateFlow<UserName>
    val role: Role
    val userId: Long
    val themeFlow: Flow<ThemeEntity>
    suspend fun setToken(newToken: String?)
    suspend fun setUserName(newUserName: String?)

    suspend fun setRole(role: Role)
    suspend fun setUserId(id: Long?)
    suspend fun saveTheme(theme: ThemeEntity)
}

typealias UserName = String?