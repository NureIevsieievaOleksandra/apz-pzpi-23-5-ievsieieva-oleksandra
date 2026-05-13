package ua.nure.smartlight.ui.analytics.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.cartesian.CartesianChartHost
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberBottom
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberEnd
import com.patrykandpatrick.vico.compose.cartesian.axis.rememberStart
import com.patrykandpatrick.vico.compose.cartesian.layer.rememberLineCartesianLayer
import com.patrykandpatrick.vico.compose.cartesian.rememberCartesianChart
import com.patrykandpatrick.vico.compose.cartesian.rememberVicoScrollState
import com.patrykandpatrick.vico.compose.common.component.rememberShapeComponent
import com.patrykandpatrick.vico.compose.common.fill
import com.patrykandpatrick.vico.core.cartesian.axis.Axis
import com.patrykandpatrick.vico.core.cartesian.axis.HorizontalAxis
import com.patrykandpatrick.vico.core.cartesian.axis.VerticalAxis
import com.patrykandpatrick.vico.core.cartesian.data.CartesianChartModelProducer
import com.patrykandpatrick.vico.core.cartesian.data.CartesianLayerRangeProvider
import com.patrykandpatrick.vico.core.cartesian.data.CartesianValueFormatter
import com.patrykandpatrick.vico.core.cartesian.data.lineSeries
import com.patrykandpatrick.vico.core.cartesian.layer.LineCartesianLayer
import com.patrykandpatrick.vico.core.common.component.TextComponent
import com.patrykandpatrick.vico.core.common.data.ExtraStore
import com.patrykandpatrick.vico.core.common.shader.ShaderProvider.Companion.verticalGradient
import com.patrykandpatrick.vico.core.common.shape.CorneredShape
import ua.nure.smartlight.R
import ua.nure.smartlight.repository.analytics.model.IotStatisticsReview
import ua.nure.smartlight.ui.theme.AppTheme
import java.time.format.DateTimeFormatter

@Composable
fun PowerConsumptionAndTemperatureItem(
    modifier: Modifier = Modifier,
    iotStatisticsReview: IotStatisticsReview
) {
    Column(
        modifier = modifier
    ) {
        Text(
            modifier = Modifier
                .padding(vertical = AppTheme.dimension.normal),
            text = String.format(stringResource(R.string.powerConsumption), iotStatisticsReview.powerConsumption),
            style = AppTheme.typography.regular
        )

        val modelProducer = remember { CartesianChartModelProducer() }
        val bottomAxisLabelKeys = ExtraStore.Key<List<String>>()

        LaunchedEffect(key1 = iotStatisticsReview.iotTemperatureChart) {
            if(iotStatisticsReview.iotTemperatureChart.isNotEmpty()) {
                modelProducer.runTransaction {
                    lineSeries {
                        series(y = iotStatisticsReview.iotTemperatureChart.map { it.temperature })
                    }
                    extras {
                        it[bottomAxisLabelKeys] = iotStatisticsReview.iotTemperatureChart.map {
                            it.timestamp.format(DateTimeFormatter.ofPattern("MM dd HH:mm"))
                        }
                    }
                }
            }
        }

        CartesianChartHost(
            modifier = Modifier
                .padding(
                    horizontal = AppTheme.dimension.normal
                )
                .height(276.dp),
            chart = rememberCartesianChart(
                rememberLineCartesianLayer(
                    rangeProvider = CartesianLayerRangeProvider.auto(),
                    lineProvider = LineCartesianLayer.LineProvider.series(
                        lines = listOf(
                            LineCartesianLayer.Line(
                                pointProvider = LineCartesianLayer.PointProvider.single(
                                    LineCartesianLayer.Point(
                                        component = rememberShapeComponent(
                                            shape = CorneredShape.Pill,
                                            fill = fill(Color.Red)
                                        ),
                                        sizeDp = 6F
                                    )),
                                stroke = LineCartesianLayer.LineStroke.Continuous(thicknessDp = 1F),
                                fill = LineCartesianLayer.LineFill.single(
                                    fill = fill(Color.Red)
                                ),
                                areaFill = LineCartesianLayer.AreaFill.single(
                                    fill = fill(
                                        shaderProvider = verticalGradient(
                                            listOf(
                                                Color.Red.copy(alpha = 0.4F).toArgb(),
                                                Color.Red.copy(alpha = 0F).toArgb()
                                            ).toIntArray()
                                        )
                                    )
                                ),
                            ),

                            )
                    ),
                    verticalAxisPosition = Axis.Position.Vertical.Start,

                    ),
                startAxis = VerticalAxis.rememberStart(
                    valueFormatter = CartesianValueFormatter { context, value, _ ->
                        value.toString()
                    },
                    label = TextComponent(color = AppTheme.color.foreground.toArgb()),
                ),
                bottomAxis = HorizontalAxis.rememberBottom(
                    valueFormatter = CartesianValueFormatter { context, value, _ ->
                        runCatching {
                            context.model.extraStore[bottomAxisLabelKeys][value.toInt()]
                        }.getOrNull() ?: "error"
                    }
                ),
            ),
            modelProducer = modelProducer,
            scrollState = rememberVicoScrollState(scrollEnabled = true)
        )


    }



}