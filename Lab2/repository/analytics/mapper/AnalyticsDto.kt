package ua.nure.smartlight.repository.analytics.mapper

import ua.nure.smartlight.repository.analytics.dto.AnalyticsDto
import ua.nure.smartlight.repository.analytics.model.Analytics

fun AnalyticsDto.toModel() =
    Analytics(
        colorContest = colorContest.map { it.toModel() },
        mathExpectationR = mathExpectationR,
        mathExpectationG = mathExpectationG,
        mathExpectationB = mathExpectationB,
        varianceR = varianceR,
        varianceG = varianceG,
        varianceB = varianceB,
        iotStats = iotStats.map {
            it.toModel()
        }
    )