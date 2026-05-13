package ua.nure.smartlight.ui.analytics

import ua.nure.smartlight.db.entity.Group
import ua.nure.smartlight.navigation.Screen
import ua.nure.smartlight.repository.analytics.model.Analytics

object Analytics {
    sealed interface Event {
        data class OnNavigate(val route: Screen) : Event
        data object OnBack : Event
    }

    sealed interface Action {
        data object OnBack : Action
        data class OnNavigate(val route: Screen) : Action
    }

    data class State(
        val inProgress: Boolean = false,
        val analytics: Analytics? = null
    )
}