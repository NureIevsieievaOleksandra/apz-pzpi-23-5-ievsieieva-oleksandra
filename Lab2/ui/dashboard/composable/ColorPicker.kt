package ua.nure.smartlight.ui.dashboard.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import com.github.skydoves.colorpicker.compose.rememberColorPickerController
import okhttp3.internal.http.StatusLine
import ua.nure.smartlight.ui.theme.AppTheme

@Composable
fun SmartLineColorPicker(
    modifier: Modifier = Modifier,
    color: Color,
    brightness: Int,
    onColorChange: (Color) -> Unit,
    onBrightnessChange: (Int) -> Unit

    ) {
    val controller = rememberColorPickerController()

    Column(
        modifier = modifier
    ) {
        var sliderValue by remember { mutableStateOf(brightness.toFloat()) }
        HsvColorPicker(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp),
            controller = controller,
            initialColor = color,
            onColorChanged = { colorEnvelope ->
                onColorChange(colorEnvelope.color)
            }
        )

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = AppTheme.dimension.normal)
            ,
            value = sliderValue,
            onValueChange = {
                sliderValue = it
                onBrightnessChange(it.toInt())
            },
            valueRange = 0f..255f,
            steps = 254

        )


    }
}

@Preview
@Composable
fun SLColorPickerPreview(modifier: Modifier = Modifier) {
    AppTheme {
        SmartLineColorPicker(
            modifier = Modifier,
            color = Color.Green,
            brightness = 255,
            onColorChange = {},
            onBrightnessChange = {}
        )
    }

}