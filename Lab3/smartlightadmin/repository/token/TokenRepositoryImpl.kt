package ua.nure.smartlight.repository.token

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ua.nure.smartlightadmin.repository.auth.dto.Role
import ua.nure.smartlightadmin.repository.token.TokenRepository
import ua.nure.smartlightadmin.repository.token.UserName

class TokenRepositoryImpl (

) : TokenRepository {
    override val token
        get() = _token
    override val userName: String?
        get() = _userName
    private val _userNameFlow = MutableStateFlow<UserName>(null)
    override val userNameFlow: StateFlow<UserName>
        get() = _userNameFlow.asStateFlow()

    override val role: Role
        get() = _role ?: Role.Undefined

    override val userId: Long
        get() = _id ?: -1L


    override suspend fun setToken(newToken: String?): Unit = withContext(Dispatchers.Default) {
        _token = newToken
    }

    override suspend fun setUserName(newUserName: String?) {
        _userName = newUserName
        _userNameFlow.emit(newUserName)
    }

    override suspend fun setRole(role: Role) {
        _role = role
    }

    override suspend fun setUserId(id: Long?) {
        _id = id
    }

    companion object {
        private var _token: String? = null
        private var _userName: String? = null
        private var _role: Role?  = null
        private var _id: Long? = null
    }
}