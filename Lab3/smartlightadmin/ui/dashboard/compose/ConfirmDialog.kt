package ua.nure.smartlightadmin.ui.dashboard.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import org.jetbrains.compose.resources.stringResource
import smartlightadmin.composeapp.generated.resources.Res
import smartlightadmin.composeapp.generated.resources.confirm
import smartlightadmin.composeapp.generated.resources.confirmDialogMessage
import ua.nure.smartlightadmin.ui.composable.SLButton
import ua.nure.smartlightadmin.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmDialog(
    onDismiss: () -> Unit,
    onAction: () -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = AppTheme.color.background,
        scrimColor = AppTheme.color.backgroundAccent.copy(
            alpha = 0.5F
        )
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.dimension.normal,
                    vertical = AppTheme.dimension.normal
                )
            ,
            text = stringResource(Res.string.confirmDialogMessage),
            style = AppTheme.typography.large.copy(
                textAlign = TextAlign.Center
            )
        )
        SLButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.dimension.normal,
                    vertical = AppTheme.dimension.large
                ),
            text = stringResource(Res.string.confirm)
        ) {
            onAction()
        }
    }
}