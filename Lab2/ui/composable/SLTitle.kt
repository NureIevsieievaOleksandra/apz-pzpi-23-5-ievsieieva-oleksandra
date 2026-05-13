package ua.nure.smartlight.ui.composable

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.nure.smartlight.R
import ua.nure.smartlight.ui.theme.AppTheme

@Composable
fun SLTitle(
    modifier: Modifier = Modifier,
    title: String,
    trailingIcon: Int? = null,
    onTrailingAction: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(36.dp)
            .padding(horizontal = AppTheme.dimension.normal)
        ,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier
                .weight(1F),
            text = title,
            style = AppTheme.typography.regular
        )
        trailingIcon?.let { icon ->
            Icon(
                modifier = Modifier
                    .clickable(onClick = onTrailingAction),
                painter = painterResource(icon),
                contentDescription = null,
                tint = AppTheme.color.foreground
            )
        }
    }
}

@Preview
@Composable
private fun SLTitlePreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            SLTitle(
                title = "Dashboard",
                trailingIcon = R.drawable.plus
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SLTitleDarkPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            SLTitle(
                title = "Dashboard",
                trailingIcon = R.drawable.plus
            )
        }
    }
}