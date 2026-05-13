package ua.nure.smartlight.ui.settings.compose

import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ua.nure.smartlight.R
import ua.nure.smartlight.ui.theme.AppTheme

@Composable
fun SettingsItem(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    @StringRes title: Int,
    comment: String? = null,
    color: Color = AppTheme.color.active,
    onClick: () -> Unit = {},
) {
    Row(
        modifier = modifier
            .padding(vertical = AppTheme.dimension.small)
            .clickable {
                onClick()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .size(24.dp),
            painter = painterResource(icon),
            contentDescription = null,
            tint = color
        )
        Text(
            modifier = Modifier
                .padding(horizontal = AppTheme.dimension.normal),
            text = stringResource(title),
            style = AppTheme.typography.regular.copy(
                fontSize = 16.sp,
                fontWeight = FontWeight(600),
                color = AppTheme.color.foreground
            )
        )
        Spacer(
            modifier = Modifier.weight(1F)
        )

        comment?.let {
            Text(
                modifier = Modifier
                    .padding(end = AppTheme.dimension.normal),
                text = comment,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = AppTheme.typography.large.copy(
                    color = AppTheme.color.grey
                )
            )
        }

        Icon(
            painter = painterResource(R.drawable.arrow_forward),
            contentDescription = null,
            tint = AppTheme.color.active
        )
    }
}

@Preview()
@Composable
private fun SettingsItemPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            SettingsItem(
                icon = R.drawable.moon,
                title = R.string.darkTheme,
                comment = "Notification",
                onClick = {}
            )

        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
private fun SettingsItemRightPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            SettingsItem(
                icon = R.drawable.moon,
                title = R.string.darkTheme,
                comment = "Notification",
                onClick = {}
            )

        }
    }
}



