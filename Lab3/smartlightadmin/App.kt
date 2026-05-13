package ua.nure.smartlightadmin

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import ua.nure.smartlightadmin.navigation.NavGraph
import ua.nure.smartlightadmin.ui.theme.AppTheme

val LocalPlatformProvider = staticCompositionLocalOf<Platform> {
    error("Platform is not provided")
}

@Composable
@Preview
fun App() {
    CompositionLocalProvider(
        LocalPlatformProvider provides getPlatform()
    ) {
        AppTheme {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
            ) { padding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = AppTheme.color.background)
                        .padding(padding),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(
                        modifier = Modifier
                            .widthIn(max = 800.dp),
                    ) {
                        NavGraph(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(padding),
                            navController = navController
                        )

                    }
                }
            }
        }
    }
}

