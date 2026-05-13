package ua.nure.smartlight.ui.settings.compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import ua.nure.smartlight.R
import ua.nure.smartlight.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThemeDialog(
    modifier: Modifier = Modifier,
    theme: ThemeEntity? = null,
    onDismissRequest: () -> Unit,
    onSelect: (ThemeEntity) -> Unit,
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
            modifier = modifier
                .padding(horizontal = AppTheme.dimension.normal),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            var selected by remember { mutableStateOf(theme ?: ThemeEntity.System) }

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(R.string.appearance),
                style = AppTheme.typography.small.copy(
                )
            )

            listOf(
                Pair(ThemeEntity.System, stringResource(R.string.system)),
                Pair(ThemeEntity.Laght, stringResource(R.string.light)),
                Pair(ThemeEntity.Dark, stringResource(R.string.dark))
            ).forEach { (type, name) ->
                ThemeItem(
                    text = name,
                    isSelected = selected == type
                ) {
                    selected = type
                    onSelect(type)
                }
            }
            Spacer(modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.dimension.normal))
        }
    }
}

@Composable
fun ThemeItem(
    modifier: Modifier = Modifier,
    isSelected: Boolean = false,
    text: String,
    onSelect: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = AppTheme.dimension.small)
            .clickable(onClick = onSelect),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Radio(
            isActive = isSelected
        )
        Text(
            modifier = Modifier
                .padding(start = AppTheme.dimension.small)
                .weight(1F),
            text = text,
            style = AppTheme.typography.regular
        )
    }
}

enum class ThemeEntity {
    System, Laght, Dark
}

@Preview
@Composable
fun ThemePreviewDialog(modifier: Modifier = Modifier) {
    AppTheme {
        ThemeDialog(
            onDismissRequest = {},
            onSelect = {}
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun ThemePreviewDarkDialog(modifier: Modifier = Modifier) {
    AppTheme {
        ThemeDialog(
            onDismissRequest = {},
            onSelect = {}
        )
    }
}