package ua.nure.smartlight.repository.analytics

import ua.nure.smartlight.repository.DataError
import ua.nure.smartlight.repository.Result
import ua.nure.smartlight.repository.analytics.dto.AnalyticsDto
import ua.nure.smartlight.repository.analytics.model.Analytics

interface AnalyticsRepository {
    suspend fun loadAnalytics(): Result<Analytics, DataError>
}