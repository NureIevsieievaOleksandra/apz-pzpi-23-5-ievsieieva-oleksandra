package ua.nure.smartlight.ui.dashboard.composable

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import ua.nure.smartlight.R
import ua.nure.smartlight.ui.composable.SLButton
import ua.nure.smartlight.ui.dashboard.Dashboard
import ua.nure.smartlight.ui.theme.AppTheme

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
            text = stringResource(R.string.confirmDialogMessage),
            style = AppTheme.typography.large.copy(
                textAlign = TextAlign.Center
            )
        )
        SLButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.normal, vertical = AppTheme.dimension.normal),
            text = stringResource(R.string.confirm)
        ) {
            onAction()
        }
    }
}