package ua.nure.smartlight.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ua.nure.smartlight.ui.analytics.AnalyticsScreen
import ua.nure.smartlight.ui.dashboard.DashboardScreen

fun NavGraphBuilder.analyticsGraph(
    navController: NavController
) {
    navigation<NestedGraph.Analytics> (
        startDestination = Screen.Analytics
    ) {
        composable<Screen.Analytics> {
            AnalyticsScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}