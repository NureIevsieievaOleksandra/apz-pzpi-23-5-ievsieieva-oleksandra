package ua.nure.smartlight.ui.dashboard.composable

import android.widget.Toast
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import ua.nure.smartlight.R
import ua.nure.smartlight.db.entity.LampEntity
import ua.nure.smartlight.ui.composable.SLButton
import ua.nure.smartlight.ui.composable.SLInputField
import ua.nure.smartlight.ui.dashboard.Dashboard
import ua.nure.smartlight.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLampDialog(
    lampId: Long? = null,
    groupId: Long,
    name: String? = null,
    description: String? = null,
    r: Int? = null,
    g: Int? = null,
    b: Int? = null,
    brightness: Int? = null,
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
        val context = LocalContext.current
        val message = stringResource(R.string.lampIdIsNullMessage)
        var _lampId by remember { mutableStateOf(lampId) }
        var _name by remember { mutableStateOf(name ?: "") }
        var _description by remember { mutableStateOf(description ?: "") }
        var _color by remember {
            mutableStateOf(
                Color(
                    red = (r ?: 0) / 255f,
                    green = (g ?: 0) / 255f,
                    blue = (b ?: 0) / 255f,
                    alpha = 1f
                )
            )
        }
        var _brightness by remember { mutableStateOf(brightness) }


        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppTheme.dimension.normal),
            text = stringResource(if (isActionCreate) R.string.addLamp else R.string.editLamp),
            style = AppTheme.typography.large.copy(
                textAlign = TextAlign.Center
            )
        )

        SLInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.normal),
            value = _name,
            label = stringResource(R.string.lampName)
        ) {
            _name = it
        }

        SLInputField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.normal),
            value = _description,
            label = stringResource(R.string.description)
        ) {
            _description = it
        }

        SmartLineColorPicker(
            modifier = Modifier
                .padding(vertical = AppTheme.dimension.normal),
            color = _color,
            brightness = _brightness ?: 0,
            onColorChange = { color ->
                _color = color
            },
            onBrightnessChange = { brightness ->
                _brightness = brightness
            }
        )

        SLButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = AppTheme.dimension.large,
                    horizontal = AppTheme.dimension.normal
                ),
            text = stringResource(if (isActionCreate) R.string.add else R.string.edit)
        ) {
            onAction(
                Dashboard.Action.OnLampAddOrChange(
                    lamp = LampEntity(
                        lampId = lampId ?: -1L,
                        name = _name,
                        groupId = groupId,
                        description = _description,
                        r = (_color.red * 255).toInt(),
                        g = (_color.green * 255).toInt(),
                        b = (_color.blue * 255).toInt(),
                        brightness = _brightness,
                        active = false
                    )
                )
            )
        }
    }
}

@Preview
@Composable
fun AddLampDialogPreview(modifier: Modifier = Modifier) {
    AppTheme {
        AddLampDialog(
            lampId = 2,
            groupId = 3,
            name = "Analytic test lamp",
            description = "Test description",
            r = 114,
            g = 23,
            b = 245,
            brightness = 180,
            isActionCreate = false,
            onDismiss = { },
            onAction = {}
        )
    }

}