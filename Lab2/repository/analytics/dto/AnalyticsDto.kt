package ua.nure.smartlight.repository.analytics.dto

import kotlinx.serialization.Serializable

@Serializable
data class AnalyticsDto(
    val colorContest: List<ColorContestDto>,
    val mathExpectationR: Double,
    val mathExpectationG: Double,
    val mathExpectationB: Double,
    val varianceR: Double,
    val varianceG: Double,
    val varianceB: Double,
    val iotStats: List<IotStatisticsReviewDto>

)