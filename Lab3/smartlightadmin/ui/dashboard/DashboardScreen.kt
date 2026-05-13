package ua.nure.smartlightadmin.ui.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import org.jetbrains.compose.resources.stringResource
import ua.nure.smartlightadmin.repository.user.dto.UserDto
import ua.nure.smartlightadmin.ui.composable.SmartLightScreen
import ua.nure.smartlightadmin.ui.dashboard.compose.ConfirmDialog
import ua.nure.smartlightadmin.ui.dashboard.pages.AdminPage
import ua.nure.smartlightadmin.ui.dashboard.pages.GroupPage
import ua.nure.smartlightadmin.ui.dashboard.pages.UserPage
import ua.nure.smartlightadmin.ui.theme.AppTheme

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when (it) {
                Dashboard.Event.OnBack -> navController.navigateUp()
                is Dashboard.Event.OnNavigate -> navController.navigate(route = it.route)
            }
        }
    }

    DashboardScreenContent(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
private fun DashboardScreenContent(
    state: Dashboard.State,
    onAction: (Dashboard.Action) -> Unit
) {
    SmartLightScreen {
        val pagerState = rememberPagerState(pageCount = { state.pagerItems.size })

        LaunchedEffect(key1 = state.selectedIndex) {
            pagerState.animateScrollToPage(state.selectedIndex)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            state.pagerItems.forEach { item ->
                Text(
                    modifier = Modifier
                        .padding(start = AppTheme.dimension.large)
                        .padding(vertical = AppTheme.dimension.normal)
                        .clickable {
                            onAction(Dashboard.Action.OnIndexChange(index = item.index))
                        }
                    ,
                    text = stringResource(item.title),
                    style = if(item.index == pagerState.currentPage) AppTheme.typography.large.copy(
                        fontWeight = FontWeight(700),
                        color = AppTheme.color.active
                    ) else {
                        AppTheme.typography.large
                    }
                )
            }

        }

        HorizontalPager(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimension.large)
                .weight(1F),
            state = pagerState,
        ) { pageIndex ->
            when(pageIndex) {
                0 -> UserPage(
                    modifier = Modifier,
                    state = state,
                    onAction = onAction
                )
                1 -> GroupPage(
                    modifier = Modifier,
                    state = state,
                    onAction = onAction
                )
                2 -> AdminPage(
                    modifier = Modifier,
                    backups = state.backups,
                    onAction = onAction
                )
            }
        }

        if(state.showConfirmDeleteUserDialog) {
            ConfirmDialog(
                onDismiss = {
                    onAction(Dashboard.Action.OnShowConfirmDeleteUserDialog)
                },
                onAction = {
                    onAction(Dashboard.Action.OnDeleteUser)
                }
            )
        }

        if(state.showConfirmDeleteGroupDialog) {
            ConfirmDialog(
                onDismiss = {
                    onAction(Dashboard.Action.OnShowConfirmDeleteGroupDialog)
                },
                onAction = {
                    onAction(Dashboard.Action.OnDeleteGroup)
                }
            )
        }

        if(state.showDeleteBackupConfirm) {
            ConfirmDialog(
                onDismiss = {
                    onAction(Dashboard.Action.OnDismissDeleteBackup)
                },
                onAction = {
                    onAction(Dashboard.Action.OnConfirmDeleteBackup)
                }
            )
        }
        if(state.showRestoreBackupConfirmDialog) {
            ConfirmDialog(
                onDismiss = {
                    onAction(Dashboard.Action.OnRestoreBackupConfirm(file = null))
                },
                onAction = {
                    onAction(Dashboard.Action.OnRestoreBackup)
                }
            )
        }
    }
}

@Preview
@Composable
private fun DashboardScreenPreview() {
    AppTheme {
        Box(modifier = Modifier) {
            DashboardScreenContent(
                state = Dashboard.State(
                    users = UserDto.userPreview
                )
            ) {}
        }
    }
}

@Preview
@Composable
private fun DashboardScreenDarkPreview() {
    AppTheme(darkTheme = true) {
        Box(modifier = Modifier) {
            DashboardScreenContent(
                state = Dashboard.State(
                    users = UserDto.userPreview
                )
            ) {}
        }
    }
}