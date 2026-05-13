package ua.nure.smartlight.ui.settings

import android.app.LocaleManager
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.LocaleList
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.getSystemService
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.nure.smartlight.R
import ua.nure.smartlight.navigation.Screen
import ua.nure.smartlight.ui.composable.SLTitle
import ua.nure.smartlight.ui.composable.SmartLightScreen
import ua.nure.smartlight.ui.dashboard.composable.ConfirmDialog
import ua.nure.smartlight.ui.settings.compose.LangDialog
import ua.nure.smartlight.ui.settings.compose.SettingsItem
import ua.nure.smartlight.ui.settings.compose.ThemeDialog
import ua.nure.smartlight.ui.theme.AppTheme

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                Settings.Event.OnBack -> navController.navigateUp()
                is Settings.Event.OnNavigate -> navController.navigate(route = it.route)
                Settings.Event.OnSignOut -> {
                    navController.navigate(route = Screen.Auth.SignIn) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            }
        }
    }

    SettingsScreenContent(
        state = state,
        onAction = viewModel::onAction
    )

}

@Composable
private fun SettingsScreenContent(
    state: Settings.State,
    onAction: (Settings.Action) -> Unit
) {
    SmartLightScreen{
        val context = LocalContext.current

        SLTitle(
            title = stringResource(R.string.settings)
        )

        Image(
            modifier = Modifier.fillMaxWidth()
                .height(200.dp),
            painter = painterResource(R.drawable.smart_light),
            contentDescription = null,
        )

        state.userName?.let { name ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = AppTheme.dimension.large)
                ,
                text = name,
                style = AppTheme.typography.large.copy(
                    fontSize = 34.sp,
                    textAlign = TextAlign.Center
                )
            )
        }

        state.role?.let { role ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                text = role.name,
                style = AppTheme.typography.regular.copy(
                    textAlign = TextAlign.Center
                )
            )
        }

        SettingsItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.dimension.normal,
                    vertical = AppTheme.dimension.small
                ),
            icon = R.drawable.moon,
            title = R.string.darkTheme,
            comment = null,
            color = AppTheme.color.active,
            onClick = {
                onAction(Settings.Action.OnShowThemeDialog)
            }
        )

        SettingsItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.normal),
            icon = R.drawable.globe,
            title = R.string.selectLanguage,
            comment = null,
            color = AppTheme.color.active,
            onClick = {
                onAction(Settings.Action.OnShowLangDialog)
            }
        )

        SettingsItem(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.normal),
            icon = R.drawable.door,
            title = R.string.signOut,
            comment = null,
            color = AppTheme.color.active,
            onClick = {
                onAction(Settings.Action.OnShowSignOutDialog)
            }
        )

        if(state.showThemeDialog) {
            ThemeDialog(
                theme = state.theme,
                onDismissRequest = {
                    onAction(Settings.Action.OnShowThemeDialog)
                },
                onSelect = { theme ->
                    onAction(Settings.Action.OnChangeTheme(theme = theme))
                }
            )
        }

        if(state.showLangDialog) {
            LangDialog(
                onDismissRequest = {
                    onAction(Settings.Action.OnShowLangDialog)
                },
                onSelect = { tag ->
                    val localeManager = context.getSystemService<LocaleManager>()
                    localeManager?.applicationLocales = LocaleList.forLanguageTags(tag)
                    onAction(Settings.Action.OnShowLangDialog)
                }
            )
        }

        if(state.showSignOUtDialog) {
            ConfirmDialog(
                onDismiss = {
                    onAction(Settings.Action.OnShowSignOutDialog)
                },
                onAction = {
                    onAction(Settings.Action.OnSignOut)
                }
            )

        }


    }
}

@Preview
@Composable
fun SettingsScreenPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            SettingsScreenContent(
                state = Settings.State()
            ) { }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun SettingsScreenDarkPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            SettingsScreenContent(
                state = Settings.State()
            ) { }
        }
    }
}