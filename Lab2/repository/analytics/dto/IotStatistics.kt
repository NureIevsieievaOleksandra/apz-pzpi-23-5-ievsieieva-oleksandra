package ua.nure.smartlight.repository.analytics.dto

import kotlinx.serialization.Serializable

@Serializable
data class IotStatisticsReviewDto (
    val lampId: Int,
    val powerConsumption: Double,
    val iotTemperatureChart: List<IotTemperatureChartDto>,
    val uptime: Long,
)

@Serializable
data class IotTemperatureChartDto (
    val temperature: Double,
    val timestamp: Long,
)