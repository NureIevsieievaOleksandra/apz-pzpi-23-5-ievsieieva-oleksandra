package ua.nure.smartlight.ui.dashboard

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import ua.nure.smartlight.R
import ua.nure.smartlight.db.entity.Group
import ua.nure.smartlight.ui.composable.SLTitle
import ua.nure.smartlight.ui.composable.SmartLightScreen
import ua.nure.smartlight.ui.dashboard.composable.AddGroupDialog
import ua.nure.smartlight.ui.dashboard.composable.AddLampDialog
import ua.nure.smartlight.ui.dashboard.composable.ConfirmDialog
import ua.nure.smartlight.ui.dashboard.composable.GroupItem
import ua.nure.smartlight.ui.theme.AppTheme

@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = Unit) {
        viewModel.event.collect {
            when(it) {
                Dashboard.Event.OnBack -> {}
                is Dashboard.Event.OnNavigate -> {
                    navController.navigate(route = it.route)
                }
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
        SLTitle(
            modifier = Modifier,
            title = stringResource(R.string.dashboard),
            trailingIcon = if(state.isAdmin) R.drawable.plus else null
        ) {
            onAction(Dashboard.Action.OnAddGroupDialog)
        }

        state.groups?.let { groups ->
            LazyColumn(
                modifier = Modifier
                    .weight(1F)
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.extraSmall)
            ) {
                items(
                    items = groups, key = { it.group.groupId }
                ) { group ->
                    GroupItem(
                        group = group,
                        isAdmin = state.isAdmin,
                        onAction = onAction
                    )
                }
            }
        }

        if(state.addGroupDialogActive) {
            AddGroupDialog(
                title = state.newGroupName,
                description = state.newGroupDescription,
                isActionCreate = state.selectedGroup == null,
                onDismiss = {
                    onAction(Dashboard.Action.OnAddGroupDialog)
                },
                onAction = onAction
            )
        }

        if(state.deleteGroupDialogActive) {
            ConfirmDialog(
                onDismiss = {
                    onAction(Dashboard.Action.OnDeleteGroupDialog())
                },
                onAction = {
                    onAction(Dashboard.Action.OnDeleteGroup)
                }
            )
        }

        if(state.deleteLampDialogActive) {
            ConfirmDialog(
                onDismiss = {
                    onAction(Dashboard.Action.OnDeleteLampDialog(lamp = null))
                },
                onAction = {
                    onAction(Dashboard.Action.OnDeleteLamp)
                }
            )
        }

        if(state.addLampDialogActive) {
            AddLampDialog(
                lampId = state.selectedLamp?.lampId,
                groupId = state.selectedGroup?.group?.groupId ?: -1L,
                name = state.selectedLamp?.name,
                description = state.selectedLamp?.description,
                r = state.selectedLamp?.r,
                g = state.selectedLamp?.g,
                b = state.selectedLamp?.b,
                brightness = state.selectedLamp?.brightness,
                isActionCreate = state.selectedLamp == null,
                onDismiss = {
                    onAction(Dashboard.Action.OnAddLampDialogDialog(group = null, lamp = null))
                },
                onAction = onAction
            )
        }

    }
}

@Preview
@Composable
fun DashboardScreenPreview(modifier: Modifier = Modifier) {
    AppTheme {
        DashboardScreenContent(
            state = Dashboard.State(
                groups = Group.groupPreview
            )
        ) { }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DashboardScreenDarkPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(modifier = Modifier.background(color = AppTheme.color.background)) {
            DashboardScreenContent(
                state = Dashboard.State(
                    groups = Group.groupPreview
                )
            ) { }
        }
    }
}