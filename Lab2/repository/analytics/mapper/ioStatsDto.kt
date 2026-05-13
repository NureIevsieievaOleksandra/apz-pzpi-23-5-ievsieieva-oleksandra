package ua.nure.smartlight.repository.analytics.mapper

import ua.nure.smartlight.extension.toLocalDateTime
import ua.nure.smartlight.repository.analytics.dto.IotStatisticsReviewDto
import ua.nure.smartlight.repository.analytics.dto.IotTemperatureChartDto
import ua.nure.smartlight.repository.analytics.model.IotStatisticsReview
import ua.nure.smartlight.repository.analytics.model.IotTemperatureChart


fun IotStatisticsReviewDto.toModel() =
    IotStatisticsReview(
        lampId = lampId,
        powerConsumption = powerConsumption,
        iotTemperatureChart = iotTemperatureChart.map { it.toModel() },
        uptime = uptime
    )

fun IotTemperatureChartDto.toModel() =
    IotTemperatureChart(
        temperature = temperature,
        timestamp = timestamp.toLocalDateTime()
    )