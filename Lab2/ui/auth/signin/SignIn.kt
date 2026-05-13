package ua.nure.smartlight.ui.auth.signin

import ua.nure.smartlight.BuildConfig
import ua.nure.smartlight.navigation.Screen

object SignIn {
    sealed interface Event {
        data class OnNavigate(val route: Screen) : Event
        data object OnBack : Event
    }

    sealed interface Action {
        data object OnBack : Action
        data class OnNavigate(val route: Screen) : Action
        data object OnSignIn : Action
        data class OnNameChange(val name: String) : Action
        data class OnPasswordChange(val password: String) : Action
    }

    data class State(
        val inProgress: Boolean = false,
        val name: String = if (BuildConfig.DEBUG) "admin" else "",
        val password: String = if (BuildConfig.DEBUG) "Secret1" else "",
        val loginError: String? = null,
    )
}