package ua.nure.smartlightadmin.ui.auth.signin

import ua.nure.smartlightadmin.navigation.Screen

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
        val name: String = "admin",
        val password: String = "Secret1",
        val loginError: String? = null,
    )
}