package ua.nure.smartlight.ui.analytics

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.patrykandpatrick.vico.compose.cartesian.axis.text
import ua.nure.smartlight.R
import ua.nure.smartlight.ui.analytics.compose.ColorCotestItem
import ua.nure.smartlight.ui.analytics.compose.PowerConsumptionAndTemperatureItem
import ua.nure.smartlight.ui.analytics.compose.RgbStatsItem
import ua.nure.smartlight.ui.composable.SLTitle
import ua.nure.smartlight.ui.composable.SmartLightScreen
import ua.nure.smartlight.ui.theme.AppTheme

@Composable
fun AnalyticsScreen(
    viewModel: AnalyticsViewModel,
    navController: NavController
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.event.collect {
            when(it) {
                Analytics.Event.OnBack -> navController.navigateUp()
                is Analytics.Event.OnNavigate -> navController.navigate(route = it.route)
            }
        }
    }

    AnalyticsScreenContent(
        state = state,
        onAction = viewModel::onAction
    )

}

@Composable
private fun AnalyticsScreenContent(
    state: Analytics.State,
    onAction: (Analytics.Action) -> Unit
) {
    SmartLightScreen{
        SLTitle(
            title = stringResource(R.string.analytics)
        )

        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(AppTheme.dimension.normal)
        ) {
            if(state.analytics?.colorContest?.isNotEmpty() == true) {
                item {
                    ColorCotestItem(
                        list = state.analytics.colorContest
                    )
                }
            }
            item {
                RgbStatsItem(
                    modifier = Modifier,
                    mathExpectationR = state.analytics?.mathExpectationR ?: 0.0,
                    mathExpectationG = state.analytics?.mathExpectationG ?: 0.0,
                    mathExpectationB = state.analytics?.mathExpectationB ?: 0.0,
                    varianceR = state.analytics?.varianceR ?: 0.0,
                    varianceG = state.analytics?.varianceG ?: 0.0,
                    varianceB = state.analytics?.varianceB ?: 0.0
                )
            }

            if(state.analytics?.iotStats?.isNotEmpty() == true) {
                item {
                    Text(
                        modifier = Modifier
                            .padding(top = AppTheme.dimension.normal)
                            .padding(horizontal = AppTheme.dimension.normal),
                        text = stringResource(R.string.powerConsumptionAndTemeratureTitle),
                        style = AppTheme.typography.large
                    )
                }

                items(items = listOf(
                    state.analytics.iotStats.first(),
                    state.analytics.iotStats.first().copy(lampId = 2),
                    state.analytics.iotStats.first().copy(lampId = 3),
                    state.analytics.iotStats.first().copy(lampId = 4),
                    state.analytics.iotStats.first().copy(lampId = 5)
                ) , key = {it.lampId} ) { review ->
                    PowerConsumptionAndTemperatureItem(
                        modifier = Modifier
                            .padding(horizontal = AppTheme.dimension.normal),
                        iotStatisticsReview = review
                    )
                }
            }

        }
    }
}

@Preview
@Composable
fun AnalyticsScreenPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            AnalyticsScreenContent(
                state = Analytics.State()
            ) { }
        }
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun AnalyticsScreenDarkPreview(modifier: Modifier = Modifier) {
    AppTheme {
        Box(
            modifier = Modifier.background(color = AppTheme.color.background)
        ) {
            AnalyticsScreenContent(
                state = Analytics.State()
            ) { }
        }
    }
}