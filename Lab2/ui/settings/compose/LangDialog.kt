package ua.nure.smartlight.ui.settings.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import ua.nure.smartlight.R
import ua.nure.smartlight.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LangDialog(
    onDismissRequest: () -> Unit,
    onSelect: (String) -> Unit,
) {
    ModalBottomSheet(
        scrimColor = AppTheme.color.backgroundAccent.copy(alpha = 0.5F),
        onDismissRequest = {
            onDismissRequest()
        },
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = AppTheme.color.background
    ) {
        listOf(
            "en" to stringResource(R.string.en),
            "uk" to stringResource(R.string.uk),
            "de" to stringResource(R.string.de),
            "fr" to stringResource(R.string.fr),
            "it" to stringResource(R.string.it),
        ).forEach { (tag, lang) ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = AppTheme.dimension.normal, vertical = AppTheme.dimension.small)
                    .clickable {
                        onSelect(tag)
                    },
                text = lang,
                style = AppTheme.typography.regular
            )
        }

        Spacer(modifier = Modifier.height(AppTheme.dimension.large))

    }
}