package ua.nure.smartlightadmin.ui.dashboard.compose

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import smartlightadmin.composeapp.generated.resources.Res
import smartlightadmin.composeapp.generated.resources.changeRoleTitle
import ua.nure.smartlightadmin.LocalPlatformProvider
import ua.nure.smartlightadmin.Platform
import ua.nure.smartlightadmin.PlatformType
import ua.nure.smartlightadmin.repository.auth.dto.Role
import ua.nure.smartlightadmin.repository.user.dto.UserDto
import ua.nure.smartlightadmin.ui.dashboard.Dashboard
import ua.nure.smartlightadmin.ui.theme.AppTheme

@Composable
fun UserItem(
    modifier: Modifier,
    user: UserDto,
    selectedUser: UserDto? = null,
    onAction: (Dashboard.Action) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(AppTheme.shape.cardShape)
                .border(
                    width = 1.dp,
                    color = if (user.userId == selectedUser?.userId) AppTheme.color.active else AppTheme.color.grey,
                    shape = AppTheme.shape.cardShape
                )
                .padding(vertical = AppTheme.dimension.normal, horizontal = AppTheme.dimension.normal),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimension.large),
                text = user.userId.toString(),
                style = AppTheme.typography.regular
            )

            Column(
                modifier = Modifier.weight(1F)
            ) {
                Text(
                    text = user.name,
                    style = AppTheme.typography.large.copy(
                        color = AppTheme.color.foreground
                    )
                )
                Text(
                    text = user.role.toString(),
                    style = AppTheme.typography.regular
                )

            }
            Icon(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimension.small)
                    .clickable {
                        onAction(Dashboard.Action.OnUserEdit(user = user))
                    },
                imageVector = Icons.Default.Edit,
                contentDescription = null,
                tint = if(user.userId == selectedUser?.userId) AppTheme.color.active else AppTheme.color.foreground
            )
            Icon(
                modifier = Modifier
                    .padding(horizontal = AppTheme.dimension.small)
                    .clickable {
                        onAction(Dashboard.Action.OnUserDelete(userDto = user))
                    },
                imageVector = Icons.Default.Close,
                contentDescription = null,
                tint = AppTheme.color.foreground
            )
        }

        AnimatedVisibility(
            visible = selectedUser?.userId == user.userId
        ) {
            Column(
                modifier = Modifier.padding(horizontal = AppTheme.dimension.normal)
            ) {
                Role.entries.forEach { role ->
                    if(role != Role.Undefined) {
                        RoleItem(
                            modifier = Modifier.padding(vertical = AppTheme.dimension.normal),
                            role = role,
                            isSelected = role == user.role
                        ) {
                            onAction(Dashboard.Action.OnSelectRole(role = role))
                        }

                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun UserItemPreview() {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            UserItem(
                modifier = Modifier,
                user = UserDto.userPreview.first(),
            ) {}
        }
    }
}

@Preview
@Composable
private fun UserItemDarkPreview() {
    CompositionLocalProvider(
        LocalPlatformProvider provides object : Platform {
            override val name: String
                get() = ""
            override val type: PlatformType
                get() = PlatformType.WEB
        }
    ) {
        AppTheme(darkTheme = true) {
            Box(
                modifier = Modifier.background(color = AppTheme.color.background)
            ) {
                UserItem(
                    modifier = Modifier,
                    user = UserDto.userPreview.first(),
                ) {}
            }
        }

    }
}