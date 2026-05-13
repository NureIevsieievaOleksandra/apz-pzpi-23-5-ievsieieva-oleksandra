package ua.nure.smartlight.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ua.nure.smartlight.ui.analytics.AnalyticsScreen
import ua.nure.smartlight.ui.settings.SettingsScreen

fun NavGraphBuilder.settingsGraph(
    navController: NavController
) {
    navigation<NestedGraph.Settings> (
        startDestination = Screen.Settings
    ) {
        composable<Screen.Settings> {
            SettingsScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}