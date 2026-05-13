package ua.nure.smartlight.navigation

import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import ua.nure.smartlight.ui.dashboard.DashboardScreen

fun NavGraphBuilder.dashboardGraph(
    navController: NavController
) {
    navigation<NestedGraph.Dashboard> (
        startDestination = Screen.Dashboard
    ) {
        composable<Screen.Dashboard> {
            DashboardScreen(
                viewModel = hiltViewModel(),
                navController = navController
            )
        }
    }
}