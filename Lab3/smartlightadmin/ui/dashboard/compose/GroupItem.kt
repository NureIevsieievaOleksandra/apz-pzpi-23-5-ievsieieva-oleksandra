package ua.nure.smartlightadmin.ui.dashboard.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import smartlightadmin.composeapp.generated.resources.Res
import smartlightadmin.composeapp.generated.resources.confirm
import smartlightadmin.composeapp.generated.resources.name
import smartlightadmin.composeapp.generated.resources.signIn
import ua.nure.smartlightadmin.repository.auth.dto.Role
import ua.nure.smartlightadmin.repository.group.dto.GroupDto
import ua.nure.smartlightadmin.ui.auth.signin.SignIn
import ua.nure.smartlightadmin.ui.composable.SLButton
import ua.nure.smartlightadmin.ui.composable.SLInputField
import ua.nure.smartlightadmin.ui.dashboard.Dashboard
import ua.nure.smartlightadmin.ui.dashboard.GroupAction
import ua.nure.smartlightadmin.ui.theme.AppTheme

@Composable
fun GroupItem(
    modifier: Modifier,
    group: GroupDto,
    selectedGroup: GroupDto? = null,
    groupAction: GroupAction = GroupAction.Unknown,
    onAction: (Dashboard.Action) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(AppTheme.shape.cardShape)
                .border(width = 1.dp, color = if(selectedGroup?.groupId == group.groupId) AppTheme.color.active else AppTheme.color.grey, shape = AppTheme.shape.cardShape)
                .padding(vertical = AppTheme.dimension.normal, horizontal = AppTheme.dimension.normal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimension.large),
                text = group.groupId.toString(),
                style = AppTheme.typography.regular
            )

            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = group.name ?: "",
                    style = AppTheme.typography.large.copy(
                        color = AppTheme.color.foreground
                    )
                )
                Text(
                    text = group.description ?: "",
                    style = AppTheme.typography.regular
                )

            }
            Icon(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimension.small)
                    .clickable {
                        onAction(Dashboard.Action.OnGroupEdit(group = group))
                    },
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = if(selectedGroup?.groupId == group.groupId) AppTheme.color.active else AppTheme.color.foreground
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimension.small)
                    .clickable {
                        onAction(Dashboard.Action.OnGroupDelete(group = group))
                    },
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = AppTheme.color.foreground
            )
        }

        AnimatedVisibility(
            visible = group.groupId == selectedGroup?.groupId && groupAction == GroupAction.Edit
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
                    label = stringResource(Res.string.name),
                    value = selectedGroup?.name ?: "",
                    onValueChange = {
                        onAction(Dashboard.Action.OnGroupNameChange(name = it))
                    }
                )

                SLInputField(
                    modifier = Modifier.fillMaxWidth()
                        .padding(
                            vertical = AppTheme.dimension.normal,
                            horizontal = AppTheme.dimension.normal
                        ),
                    label = stringResource(Res.string.name),
                    value = selectedGroup?.description ?: "",
                    onValueChange = {
                        onAction(Dashboard.Action.OnGroupDescriptionChange(description = it))
                    }
                )

                SLButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 34.dp,
                            horizontal = AppTheme.dimension.normal
                        ),
                    text = stringResource(Res.string.confirm)
                ) {
                    onAction(Dashboard.Action.OnGroupUpdate)
                }
            }
        }
    }

}