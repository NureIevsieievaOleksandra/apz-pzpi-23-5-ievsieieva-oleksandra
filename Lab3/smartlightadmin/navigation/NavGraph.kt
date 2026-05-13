package ua.nure.smartlightadmin.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.koin.compose.viewmodel.koinViewModel
import ua.nure.smartlightadmin.ui.auth.signin.SignInScreen
import ua.nure.smartlightadmin.ui.auth.signin.SignInViewModel
import ua.nure.smartlightadmin.ui.dashboard.DashboardScreen
import ua.nure.smartlightadmin.ui.dashboard.DashboardViewModel

@Composable
fun NavGraph(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Auth.SignIn
    ) {
        composable<Screen.Auth.SignIn> {
            SignInScreen(
                viewModel = koinViewModel<SignInViewModel>(),
                navController = navController
            )
        }

        composable<Screen.Dashboard> {
            DashboardScreen(
                viewModel = koinViewModel<DashboardViewModel>(),
                navController = navController
            )

        }
    }
}