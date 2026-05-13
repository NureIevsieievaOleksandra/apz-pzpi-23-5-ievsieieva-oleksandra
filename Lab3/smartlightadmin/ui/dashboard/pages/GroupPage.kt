package ua.nure.smartlightadmin.ui.dashboard.pages

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PlusOne
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import smartlightadmin.composeapp.generated.resources.Res
import smartlightadmin.composeapp.generated.resources.addGroup
import smartlightadmin.composeapp.generated.resources.confirm
import smartlightadmin.composeapp.generated.resources.groupDescription
import smartlightadmin.composeapp.generated.resources.groupName
import smartlightadmin.composeapp.generated.resources.name
import ua.nure.smartlightadmin.repository.group.dto.GroupDto
import ua.nure.smartlightadmin.ui.composable.SLButton
import ua.nure.smartlightadmin.ui.composable.SLInputField
import ua.nure.smartlightadmin.ui.dashboard.Dashboard
import ua.nure.smartlightadmin.ui.dashboard.GroupAction
import ua.nure.smartlightadmin.ui.dashboard.compose.GroupItem
import ua.nure.smartlightadmin.ui.dashboard.compose.UserItem
import ua.nure.smartlightadmin.ui.theme.AppTheme

@Composable
fun GroupPage(
    modifier: Modifier,
    state: Dashboard.State,
    onAction: (Dashboard.Action) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        var showAddGroup by remember { mutableStateOf(false) }
        var groupName by remember { mutableStateOf("") }
        var groupDescription by remember { mutableStateOf("") }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .border(width = 1.dp, color = AppTheme.color.active, shape = CircleShape)
                    .clickable {
                        showAddGroup = !showAddGroup
                    }
                ,
                imageVector = Icons.Default.Add,
                contentDescription = null,
                tint = AppTheme.color.active
            )
        }

        AnimatedVisibility(
            visible = showAddGroup
        ) {
            Column(
                modifier = Modifier.padding(horizontal = AppTheme.dimension.normal)
            ) {

                SLInputField(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            vertical = AppTheme.dimension.normal,
                            horizontal = AppTheme.dimension.normal
                        ),
                    label = stringResource(Res.string.groupName),
                    value = groupName,
                    onValueChange = {
                        groupName = it
                    }
                )

                SLInputField(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            vertical = AppTheme.dimension.normal,
                            horizontal = AppTheme.dimension.normal
                        ),
                    label = stringResource(Res.string.groupDescription),
                    value = groupDescription,
                    onValueChange = {
                        groupDescription = it
                    }
                )

                SLButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 34.dp,
                            horizontal = AppTheme.dimension.normal
                        ),
                    text = stringResource(Res.string.addGroup)
                ) {
                    onAction(Dashboard.Action.OnGroupAdd(name = groupName, description = groupDescription))
                    groupName = ""
                    groupDescription = ""
                    showAddGroup = false
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = AppTheme.dimension.normal)
                .weight(1F),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.small)
        ) {
            items(items = state.groups ?: emptyList(), key = { it.groupId ?: -1L }) { group ->
                GroupItem(
                    modifier = Modifier,
                    group = group,
                    selectedGroup = state.selectedGroup,
                    groupAction = state.groupAction,
                    onAction = onAction
                )
            }
            item {
                Spacer(modifier = Modifier.fillMaxWidth().height(AppTheme.dimension.normal))
            }
        }

    }
}

@Preview
@Composable
private fun GroupPagePreview() {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            GroupPage(
                modifier = Modifier,
                state = Dashboard.State(
                    groups = GroupDto.groupPreview,
                    selectedGroup = GroupDto.groupPreview.first(),
                    groupAction = GroupAction.Edit
                ),
            ) {}
        }
    }
}

@Preview
@Composable
private fun GroupPageDarkPreview() {
    AppTheme(darkTheme = true) {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            GroupPage(
                modifier = Modifier,
                state = Dashboard.State(
                    groups = GroupDto.groupPreview,
                    selectedGroup = GroupDto.groupPreview.first(),
                    groupAction = GroupAction.Edit
                ),
            ) {}
        }
    }
}
