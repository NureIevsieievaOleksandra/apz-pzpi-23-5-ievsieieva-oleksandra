package ua.nure.smartlight.ui.dashboard.composable

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ua.nure.smartlight.R
import ua.nure.smartlight.ui.composable.SLButton
import ua.nure.smartlight.ui.composable.SLInputField
import ua.nure.smartlight.ui.dashboard.Dashboard
import ua.nure.smartlight.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddGroupDialog(
    title: String? = null,
    description: String? = null,
    isActionCreate: Boolean = true,
    onDismiss: () -> Unit,
    onAction: (Dashboard.Action) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = AppTheme.color.background,
        scrimColor = AppTheme.color.backgroundAccent.copy(
            alpha = 0.5F
        )
    ) {
        var _title by remember { mutableStateOf(title ?: "") }
        var _desc by remember { mutableStateOf(description ?: "") }
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppTheme.dimension.normal)
            ,
            text = stringResource(if(isActionCreate) R.string.addGroup else R.string.editGroup),
            style = AppTheme.typography.large.copy(
                textAlign = TextAlign.Center
            )
        )
        SLInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.normal)
            ,
            value = _title
        ) {
            _title = it
            onAction(Dashboard.Action.OnGroupTitleChange(name = it))
        }
        SLInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.normal)
                .padding(bottom = AppTheme.dimension.normal)
            ,
            value = _desc
        ) {
            _desc = it
            onAction(Dashboard.Action.OnGroupDescriptionChange(desc = it))
        }
        SLButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = AppTheme.dimension.normal,
                    vertical = AppTheme.dimension.normal
                )
            ,
            text = stringResource(if(isActionCreate) R.string.add else R.string.edit)
        ) {
            onAction(
                if(isActionCreate) Dashboard.Action.OnAddGroup else Dashboard.Action.OnSaveGroup
            )
        }

    }

}

@Preview
@Composable
fun AddGroupDialogPreview(modifier: Modifier = Modifier) {
    AppTheme {
        AddGroupDialog(
            onDismiss = {},
            onAction = {}
        )
    }

}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AddGroupDialogDarkPreview(modifier: Modifier = Modifier) {
    AppTheme {
        AddGroupDialog(
            onDismiss = {},
            onAction = {}
        )
    }

}