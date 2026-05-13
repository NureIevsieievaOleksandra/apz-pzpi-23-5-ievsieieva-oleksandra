package ua.nure.smartlight.ui.auth.signin

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ua.nure.smartlight.navigation.Screen
import ua.nure.smartlight.repository.auth.AuthRepository
import ua.nure.smartlight.repository.onError
import ua.nure.smartlight.repository.onSuccess
import ua.nure.smartlight.repository.toErrorMessage
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel(){
    private val _state = MutableStateFlow(SignIn.State())
    val state = _state.asStateFlow()

    private val _event = MutableSharedFlow<SignIn.Event>()
    val event = _event.asSharedFlow()

    var signInJob: Job? = null

    fun onAction(action: SignIn.Action) = viewModelScope.launch {
        when(action) {
            SignIn.Action.OnBack -> _event.emit(SignIn.Event.OnBack)
            is SignIn.Action.OnNameChange -> {
                _state.update {
                    it.copy(
                        name = action.name
                    )
                }
            }
            is SignIn.Action.OnNavigate -> {
                _event.emit(SignIn.Event.OnNavigate(route = action.route))
            }
            is SignIn.Action.OnPasswordChange -> {
                _state.update {
                    it.copy(
                        password = action.password
                    )
                }
            }
            SignIn.Action.OnSignIn -> onSignIn(name = state.value.name, password = state.value.password)
        }
    }

    fun onSignIn(name: String, password: String) {
        signInJob?.cancel()
        signInJob = viewModelScope.launch {
            authRepository.signIn(
                username = name,
                password = password
            ).onError { error ->
                _state.update { s ->
                    s.copy(
                        loginError = error.toErrorMessage()
                    )
                }

            }.onSuccess {
                _event.emit(SignIn.Event.OnNavigate(route = Screen.Dashboard))
            }
        }
    }
}