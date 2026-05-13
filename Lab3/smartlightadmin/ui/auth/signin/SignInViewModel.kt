package ua.nure.smartlightadmin.ui.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.smartlightadmin.navigation.Screen
import ua.nure.smartlightadmin.repository.auth.AuthRepository
import ua.nure.smartlightadmin.repository.onError
import ua.nure.smartlightadmin.repository.onSuccess
import ua.nure.smartlightadmin.repository.toErrorMessage
import ua.nure.smartlightadmin.repository.token.TokenRepository
import ua.nure.smartlightadmin.ui.auth.signin.SignIn.Event
import ua.nure.smartlightadmin.ui.auth.signin.SignIn.Event.*

class SignInViewModel(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository

) : ViewModel() {
    private val _state = MutableStateFlow(SignIn.State())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<SignIn.Event>()
    val event = _event.asSharedFlow()

    fun onAction(action: SignIn.Action) = viewModelScope.launch {
        when(action) {
            SignIn.Action.OnBack -> _event.emit(Event.OnBack)
            is SignIn.Action.OnNavigate -> _event.emit(OnNavigate(route = action.route))
            is SignIn.Action.OnNameChange -> {
                _state.update { s ->
                    s.copy(
                        name = action.name,
                        loginError = null
                    )
                }
            }
            is SignIn.Action.OnPasswordChange -> {
                _state.update { s ->
                    s.copy(
                        password = action.password,
                        loginError = null
                    )
                }
            }
            SignIn.Action.OnSignIn -> {
                signIn(name = state.value.name, password = state.value.password)
            }
        }
    }

    private fun signIn(name: String, password: String) = viewModelScope.launch {
        authRepository.signIn(
            username = name,
            password = password
        ).onSuccess {
            _event.emit(Event.OnNavigate(route = Screen.Dashboard))
        }.onError { error ->
            _state.update { s ->
                s.copy(
                    loginError = error.toErrorMessage()
                )
            }

        }
    }
}