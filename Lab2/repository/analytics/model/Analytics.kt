package ua.nure.smartlight.repository.analytics.model

import ua.nure.smartlight.repository.analytics.dto.IotStatisticsReviewDto
import ua.nure.smartlight.repository.analytics.dto.IotTemperatureChartDto
import java.time.LocalDateTime

data class Analytics(
    val colorContest: List<ColorContest>,
    val mathExpectationR: Double,
    val mathExpectationG: Double,
    val mathExpectationB: Double,
    val varianceR: Double,
    val varianceG: Double,
    val varianceB: Double,
    val iotStats: List<IotStatisticsReview>

)

data class ColorContest(
    val r: Int? = null,
    val g: Int? = null,
    val b: Int? = null,
    val count: Long,
)

data class IotStatisticsReview (
    val lampId: Int,
    val powerConsumption: Double,
    val iotTemperatureChart: List<IotTemperatureChart>,
    val uptime: Long,
)

data class IotTemperatureChart (
    val temperature: Double,
    val timestamp: LocalDateTime,
)