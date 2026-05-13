package ua.nure.smartlight.ui.settings

import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.smartlight.repository.auth.dto.Role
import ua.nure.smartlight.repository.token.TokenRepository
import ua.nure.smartlight.ui.settings.Settings.Event.*
import javax.inject.Inject
import kotlin.collections.copy

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val tokenRepository: TokenRepository
) : ViewModel() {
    private val TAG by lazy { SettingsViewModel::class.simpleName }
    private val _state = MutableStateFlow(
        Settings.State(
            userName = tokenRepository.userName,
            role = tokenRepository.role
        )
    )
    val state = _state.onStart {
        observeTheme()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = Settings.State(
            userName = tokenRepository.userName,
            role = tokenRepository.role
        )
    )

    private val _event = MutableSharedFlow<Settings.Event>()
    val event = _event.asSharedFlow()

    private var observeThemeJob: Job? = null

    fun onAction(action: Settings.Action) = viewModelScope.launch {
        when (action) {
            Settings.Action.OnBack -> _event.emit(OnBack)
            is Settings.Action.OnNavigate -> _event.emit(OnNavigate(route = action.route))
            is Settings.Action.OnChangeTheme -> {
                _state.update { s ->
                    s.copy(
                        showThemeDialog = false
                    )
                }
                tokenRepository.saveTheme(action.theme)
            }
            Settings.Action.OnShowLangDialog -> {
                _state.update { s ->
                    s.copy(
                        showLangDialog = !s.showLangDialog
                    )
                }
            }
            Settings.Action.OnShowThemeDialog -> {
                _state.update { s ->
                    s.copy(
                        showThemeDialog = !s.showThemeDialog
                    )
                }
            }

            Settings.Action.OnSignOut -> {
                _state.update { s ->
                    s.copy(
                        showSignOUtDialog = false
                    )
                }
                tokenRepository.setToken(null)
                tokenRepository.setUserId(null)
                tokenRepository.setRole(Role.Undefined)
                tokenRepository.setUserName(null)
                _event.emit(Settings.Event.OnSignOut)
            }

            Settings.Action.OnShowSignOutDialog -> {
                _state.update { s ->
                    s.copy(
                        showSignOUtDialog = !s.showSignOUtDialog
                    )
                }
            }
        }
    }

    private fun observeTheme() {
        observeThemeJob?.cancel()
        observeThemeJob = viewModelScope.launch {
            tokenRepository.themeFlow.collect { theme ->
                _state.update { s ->
                    s.copy(
                        theme = theme
                    )
                }
            }
        }
    }
}