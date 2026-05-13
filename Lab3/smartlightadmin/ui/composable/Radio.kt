package ua.nure.smartlightadmin.ui.composable

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import ua.nure.smartlightadmin.ui.theme.AppTheme


@Composable
fun Radio(
    modifier: Modifier = Modifier,
    size: Dp = 24.dp,
    isActive: Boolean = false,
) {
    Box(
        modifier = if(isActive) {
            modifier
                .size(size)
                .clip(CircleShape)
                .border(
                    width = size * 0.33F,
                    shape = CircleShape,
                    color = AppTheme.color.active
                )
        } else {
            modifier
                .size(size)
                .clip(CircleShape)
                .border(
                    width = 2.dp,
                    shape = CircleShape,
                    color = AppTheme.color.grey
                )
        }
    ) { }

}

@Preview
@Composable
fun RadioActivePreview(modifier: Modifier = Modifier) {
    AppTheme {
        Radio(
            isActive = true
        )
    }
}

@Preview
@Composable
fun RadioPassivePreview(modifier: Modifier = Modifier) {
    AppTheme {
        Radio(
            isActive = false
        )
    }
}

@Preview
@Composable
fun RadioActiveDarkPreview(modifier: Modifier = Modifier) {
    AppTheme(darkTheme = true) {
        Radio(
            isActive = true
        )
    }
}

@Preview
@Composable
fun RadioPassiveDarkPreview(modifier: Modifier = Modifier) {
    AppTheme(darkTheme = true) {
        Radio(
            isActive = false
        )
    }
}

