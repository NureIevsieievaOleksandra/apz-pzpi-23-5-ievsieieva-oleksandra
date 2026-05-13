package ua.nure.smartlight.ui.settings

import ua.nure.smartlight.navigation.Screen
import ua.nure.smartlight.repository.auth.dto.Role
import ua.nure.smartlight.repository.token.UserName
import ua.nure.smartlight.ui.settings.compose.ThemeEntity

object Settings {
    sealed interface Event {
        data class OnNavigate(val route: Screen) : Event
        data object OnBack : Event
        data object OnSignOut : Event
    }

    sealed interface Action {
        data object OnBack : Action
        data class OnNavigate(val route: Screen) : Action
        data object OnShowThemeDialog : Action
        data object OnShowLangDialog : Action
        data class OnChangeTheme(val theme: ThemeEntity) : Action
        data object OnSignOut : Action
        data object OnShowSignOutDialog : Action
    }

    data class State(
        val inProgress: Boolean = false,
        val userName: UserName = null,
        val role: Role? = null,
        val showThemeDialog: Boolean = false,
        val showLangDialog: Boolean = false,
        val showSignOUtDialog: Boolean = false,
        val theme: ThemeEntity = ThemeEntity.System
    )
}