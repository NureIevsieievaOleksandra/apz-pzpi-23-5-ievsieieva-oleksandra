package ua.nure.smartlight.repository.token

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
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
import ua.nure.smartlight.config.PreferencesKeys
import ua.nure.smartlight.config.PreferencesKeys.appTheme
import ua.nure.smartlight.repository.auth.dto.Role
import ua.nure.smartlight.ui.settings.compose.ThemeEntity

class TokenRepositoryImpl (
    private val dataStore: DataStore<Preferences>
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

    override val themeFlow: Flow<ThemeEntity> = dataStore.data.map { preferences ->
        when(preferences[appTheme]) {
            ThemeEntity.System.name -> ThemeEntity.System
            ThemeEntity.Laght.name -> ThemeEntity.Laght
            ThemeEntity.Dark.name -> ThemeEntity.Dark
            else -> ThemeEntity.System
        }
    }.flowOn(Dispatchers.IO)

    init {
        CoroutineScope(Dispatchers.IO).launch {
            _token = dataStore.data.map {
                it[PreferencesKeys.token]
            }.firstOrNull()
        }
    }

    override suspend fun setToken(newToken: String?): Unit = withContext(Dispatchers.IO) {
        _token = newToken
        if (newToken == null) {
            dataStore.edit {
                it.remove(PreferencesKeys.token)
            }
        } else {
            dataStore.edit {
                it[PreferencesKeys.token] = newToken
            }
        }
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

    override suspend fun saveTheme(theme: ThemeEntity): Unit = withContext(Dispatchers.IO) {
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.appTheme] = theme.name
        }
    }

    companion object {
        private var _token: String? = null
        private var _userName: String? = null
        private var _role: Role?  = null
        private var _id: Long? = null
    }
}