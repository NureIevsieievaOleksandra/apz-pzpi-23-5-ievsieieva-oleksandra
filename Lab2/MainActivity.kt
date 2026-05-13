package ua.nure.smartlight

import android.R
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import ua.nure.smartlight.navigation.NavGraph
import ua.nure.smartlight.navigation.Screen
import ua.nure.smartlight.navigation.topLevelRoutes
import ua.nure.smartlight.ui.MainActivityViewModel
import ua.nure.smartlight.ui.settings.compose.ThemeEntity
import ua.nure.smartlight.ui.theme.AppColors
import ua.nure.smartlight.ui.theme.AppDimension
import ua.nure.smartlight.ui.theme.AppShape
import ua.nure.smartlight.ui.theme.AppTheme
import ua.nure.smartlight.ui.theme.AppTypography

val LocalAppColorScheme = staticCompositionLocalOf { AppColors() }
val LocalAppTypography = staticCompositionLocalOf { AppTypography() }
val LocalAppDimension = staticCompositionLocalOf { AppDimension() }
val LocalAppShape = staticCompositionLocalOf { AppShape() }

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            val viewModel = hiltViewModel<MainActivityViewModel>()
            val isDarkTheme by viewModel.isDarkTheme.collectAsStateWithLifecycle()

            AppTheme(
                darkTheme =
                    when (isDarkTheme) {
                        ThemeEntity.System -> isSystemInDarkTheme()
                        ThemeEntity.Laght -> false
                        ThemeEntity.Dark -> true
                    }
            ) {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        if(showBottomNavigationBar(navDestination = currentDestination)) {
                            BottomAppBar (
                                modifier = Modifier
                                    .fillMaxWidth(),
                                containerColor = AppTheme.color.background

                            ) {
                                topLevelRoutes.forEach { topLevelRoute ->
                                    Column(
                                        modifier = Modifier
                                            .weight(1F)
                                            .clickable {
                                            navController.navigate(route = topLevelRoute.route)
                                        },
                                        horizontalAlignment = Alignment.CenterHorizontally
                                    ) {
                                        Icon(
                                            modifier = Modifier.size(45.dp),
                                            painter = painterResource(topLevelRoute.selectedIcon),
                                            contentDescription = null,
                                            tint = if(currentDestination?.hierarchy?.any { it.hasRoute(route = topLevelRoute.route::class) } == true) {
                                                AppTheme.color.active
                                            } else {
                                                AppTheme.color.grey
                                            }
                                        )
                                        Text(
                                            text = stringResource(topLevelRoute.title),
                                            style = AppTheme.typography.small.copy(
                                                color = if(currentDestination?.hierarchy?.any { it.hasRoute(route = topLevelRoute.route::class) } == true) AppTheme.color.active else AppTheme.color.grey
                                            )
                                        )
                                    }
                                }
                            }
                        }
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = AppTheme.color.background)
                    ) {
                        NavGraph(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(innerPadding)
                                ,
                            navController = navController
                        )
                    }

                }
            }
        }
    }
}

fun showBottomNavigationBar(navDestination: NavDestination?) =
    navDestination?.let { destination ->
        destination.route in listOf(
            Screen.Dashboard::class.qualifiedName,
            Screen.Analytics::class.qualifiedName,
            Screen.Settings::class.qualifiedName,
        )
    } ?: false