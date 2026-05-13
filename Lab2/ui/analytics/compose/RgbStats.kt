package ua.nure.smartlight.ui.analytics.compose

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ua.nure.smartlight.R
import ua.nure.smartlight.ui.theme.AppTheme

@Composable
fun RgbStatsItem(
    modifier: Modifier = Modifier,
    mathExpectationR: Double,
    mathExpectationG: Double,
    mathExpectationB: Double,
    varianceR: Double,
    varianceG: Double,
    varianceB: Double,
) {
    Column(
        modifier = modifier.padding(horizontal = AppTheme.dimension.normal),
        verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.small)
    ) {
        Text(
            text = stringResource(R.string.rgbStats),
            style = AppTheme.typography.large
        )
        Row(horizontalArrangement = Arrangement.spacedBy(AppTheme.dimension.small)) {
            RgbStatCard(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.matExpectationTitle),
                r = mathExpectationR,
                g = mathExpectationG,
                b = mathExpectationB
            )
            RgbStatCard(
                modifier = Modifier.weight(1f),
                label = stringResource(R.string.varianceTitle),
                r = varianceR,
                g = varianceG,
                b = varianceB
            )
        }
    }
}

@Composable
private fun RgbStatCard(
    modifier: Modifier = Modifier,
    label: String,
    r: Double,
    g: Double,
    b: Double,
) {
    Card(
        modifier = modifier
//            .clip(AppTheme.shape.cardShape)
//            .border(width = 1.dp, color = AppTheme.color.active, shape = AppTheme.shape.cardShape)
        ,
        colors = CardDefaults.cardColors().copy(
            containerColor = AppTheme.color.backgroundAccent
        )
    ) {
        Column(
            modifier = Modifier
                .padding(AppTheme.dimension.normal),
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.small)
        ) {
            Text(
                text = label,
                style = AppTheme.typography.small
            )
            RgbRow(label = "R", value = r, color = Color.Red)
            RgbRow(label = "G", value = g, color = Color.Green)
            RgbRow(label = "B", value = b, color = Color.Blue)
        }
    }
}

@Composable
private fun RgbRow(
    label: String,
    value: Double,
    color: Color
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .background(color = color, shape = CircleShape)
        )
        Text(
            text = "$label: ${"%.2f".format(value)}",
            style = AppTheme.typography.small
        )
    }
}


@Preview
@Composable
fun RgbStatsPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            RgbStatsItem(
                modifier = modifier,
                mathExpectationR = 154.03,
                mathExpectationG = 154.03,
                mathExpectationB = 154.03,
                varianceR = 11514.269100000001,
                varianceG = 11514.269100000001,
                varianceB = 11514.269100000001
            )
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun RgbStatsDarkPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            RgbStatsItem(
                modifier = modifier,
                mathExpectationR = 154.03,
                mathExpectationG = 154.03,
                mathExpectationB = 154.03,
                varianceR = 11514.269100000001,
                varianceG = 11514.269100000001,
                varianceB = 11514.269100000001
            )

        }
    }
}