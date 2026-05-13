package ua.nure.smartlight.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ua.nure.smartlight.R

data class TopLevelRoute<T : Any>(
    val route: T,
    @param:StringRes val title: Int,
    @param:DrawableRes val selectedIcon: Int,
    @param:DrawableRes val unselectedIcon: Int,
)

val topLevelRoutes = listOf<TopLevelRoute<NestedGraph>>(
    TopLevelRoute(
        route = NestedGraph.Dashboard,
        title = R.string.dashboard,
        selectedIcon = R.drawable.lamp_active,
        unselectedIcon = R.drawable.lamp_passive
    ),
    TopLevelRoute(
        route = NestedGraph.Analytics,
        title = R.string.analytics,
        selectedIcon = R.drawable.analytics_active,
        unselectedIcon = R.drawable.analytics_passive

    ),
    TopLevelRoute(
        route = NestedGraph.Settings,
        title = R.string.settings,
        selectedIcon = R.drawable.settings_active,
        unselectedIcon = R.drawable.settings_passive
    )
)