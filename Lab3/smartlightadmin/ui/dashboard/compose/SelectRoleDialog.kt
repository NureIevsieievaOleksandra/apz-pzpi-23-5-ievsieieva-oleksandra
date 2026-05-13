package ua.nure.smartlightadmin.ui.dashboard.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.ktor.websocket.Frame
import org.jetbrains.compose.resources.stringResource
import smartlightadmin.composeapp.generated.resources.Res
import smartlightadmin.composeapp.generated.resources.changeRoleTitle
import ua.nure.smartlightadmin.repository.auth.dto.Role
import ua.nure.smartlightadmin.ui.composable.Radio
import ua.nure.smartlightadmin.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectRoleDialog(
    role: Role = Role.User,
    onDismissRequest: () -> Unit,
    onSelect: (Role) -> Unit,
) {
    ModalBottomSheet(
        scrimColor = AppTheme.color.backgroundAccent.copy(alpha = 0.5F),
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = AppTheme.color.background
    ) {
        Column(
            modifier = Modifier.padding(horizontal = AppTheme.dimension.normal)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = AppTheme.dimension.large)
                ,
                text = stringResource(Res.string.changeRoleTitle),
                style = AppTheme.typography.large.copy(
                    textAlign = TextAlign.Center
                )
            )
            RoleItem(
                modifier = Modifier.padding(vertical = AppTheme.dimension.normal),
                role = Role.User,
                isSelected = role == Role.User
            ) {
                onSelect(role)
            }
            RoleItem(
                modifier = Modifier.padding(vertical = AppTheme.dimension.normal)
                    .padding(bottom = AppTheme.dimension.large),
                role = Role.Admin,
                isSelected = role == Role.User
            ) {
                onSelect(role)
            }
        }

    }
}

@Composable
fun RoleItem(
    modifier: Modifier = Modifier,
    role: Role,
    isSelected: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Radio(
            modifier = Modifier.padding(horizontal = AppTheme.dimension.normal),
            isActive = isSelected
        )
        Text(
            text = role.toString(),
            style = AppTheme.typography.regular
        )

    }
}

@Preview
@Composable
private fun SelectRoleDialogPreview() {
    AppTheme {
        SelectRoleDialog(
            role = Role.Admin,
            onDismissRequest = {},
            onSelect = {}
        )
    }
}

@Preview
@Composable
private fun SelectRoleDialogDarkPreview() {
    AppTheme(darkTheme = true) {
        SelectRoleDialog(
            role = Role.Admin,
            onDismissRequest = {},
            onSelect = {}
        )
    }
}