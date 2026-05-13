package ua.nure.smartlight.ui.analytics.compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberColumnCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.component.rememberLineComponent
import com.patrykandpatrick.vico.compose.common.component.rememberTextComponent
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.columnSeries
import com.patrykandpatrick.vico.core.common.component.LineComponent
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import ua.nure.smartlight.repository.analytics.model.ColorContest
import ua.nure.smartlight.ui.theme.AppTheme
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.ColumnCartesianLayerModel
import com.patrykandpatrick.vico.core.cartesian.layer.ColumnCartesianLayer
import com.patrykandpatrick.vico.core.cartesian.marker.ColumnCartesianLayerMarkerTarget
import com.patrykandpatrick.vico.core.cartesian.marker.DefaultCartesianMarker
import com.patrykandpatrick.vico.core.common.Insets
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import ua.nure.smartlight.R

@Composable
fun ColorCotestItem(
    modifier: Modifier = Modifier,
    list: List<ColorContest>
) {
    val columnsModelProducer = remember { CartesianChartModelProducer() }
    val bottomAxisLabelKeys = ExtraStore.Key<List<String>>()
    val valueSpanStyle = AppTheme.typography.small.toSpanStyle()
    var lineComponents by remember { mutableStateOf<List<LineComponent>>(emptyList()) }

    LaunchedEffect(key1 = list) {
        if (list.isNotEmpty()) {
            columnsModelProducer.runTransaction {
                columnSeries {
                    series(list.map { it.count })
                }
                extras {
                    it[bottomAxisLabelKeys] = list.map { it.count.toString() }
                }
            }
        }
    }

    lineComponents = list.map { item ->
        rememberLineComponent(
            fill = fill(
                color = Color(
                    red = item.r ?: 0,
                    green = item.g ?: 0,
                    blue = item.b ?: 0,
                )
            ),
            thickness = 8.dp,
            shape = CorneredShape.rounded(topLeftDp = 8F, topRightDp = 8F, bottomLeftDp = 0F, bottomRightDp = 0F)
        )
    }

    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = AppTheme.dimension.normal, vertical = AppTheme.dimension.normal)
        ,
        text = stringResource(R.string.colorContestTitle),
        style = AppTheme.typography.large
    )

    CartesianChartHost(
        modifier = Modifier
            .padding(
                horizontal = AppTheme.dimension.normal
            )
            .height(196.dp),
        chart = rememberCartesianChart(
            rememberColumnCartesianLayer(
                columnProvider = remember(lineComponents) {
                    getColumnProvider(list = lineComponents)
                }
            ),
            startAxis = VerticalAxis.rememberStart(
                valueFormatter = CartesianValueFormatter { context, value, _ ->
                    value.toString()
                }
            ),
            bottomAxis = HorizontalAxis.rememberBottom(
                valueFormatter = CartesianValueFormatter { context, value, _ ->
                    runCatching {
                        context.model.extraStore[bottomAxisLabelKeys][value.toInt()]
                    }.getOrNull() ?: "error"
                }
            ),
            marker = DefaultCartesianMarker(
                label = rememberTextComponent(
                    color = AppTheme.color.background,
                    background = rememberLineComponent(
                        fill = fill(AppTheme.color.foreground),
                        shape = CorneredShape.rounded(allDp = 8F)
                    ),
                    padding = Insets(allDp = 4F),
                    lineCount = 1
                ),
//                valueFormatter = { context, targets ->
//                    val target = targets.firstOrNull()
//                    if(target is ColumnCartesianLayerMarkerTarget) {
//                        val entry = target.columns.firstOrNull()?.entry
//                        if(entry != null) {
//                            val y = entry.y
//                            val comment = context.model.extraStore[bottomAxisLabelKeys][entry.x.toInt()]
//                            buildAnnotatedString {
//                                withStyle(style = valueSpanStyle) {
//                                    append(comment)
//                                }
//                            }
//                        } else {
//                            ""
//                        }
//                    } else {
//                        ""
//                    }
//                },
                labelPosition = DefaultCartesianMarker.LabelPosition.Top,
            )
        ),
        modelProducer = columnsModelProducer,
        scrollState = rememberVicoScrollState(scrollEnabled = true)
    )
}

fun getColumnProvider(list: List<LineComponent>) =
    object : ColumnCartesianLayer.ColumnProvider {
        override fun getColumn(
            entry: ColumnCartesianLayerModel.Entry,
            seriesIndex: Int,
            extraStore: ExtraStore
        ): LineComponent =
            list[entry.x.toInt()]


        override fun getWidestSeriesColumn(
            seriesIndex: Int,
            extraStore: ExtraStore
        ): LineComponent = list[seriesIndex]
    }