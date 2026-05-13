package ua.nure.smartlight.repository.analytics

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.nure.smartlight.di.DbDeliveryDispatcher
import ua.nure.smartlight.repository.DataError
import ua.nure.smartlight.repository.Result
import ua.nure.smartlight.repository.analytics.dto.AnalyticsDto
import ua.nure.smartlight.repository.analytics.mapper.toModel
import ua.nure.smartlight.repository.analytics.model.Analytics
import ua.nure.smartlight.repository.db.DbRepository
import ua.nure.smartlight.repository.map
import ua.nure.smartlight.repository.safeCall

class AnalyticsRepositoryImpl(
    private val httpClient: HttpClient,
    private val dbRepository: DbRepository,
    @DbDeliveryDispatcher private val dbDeliveryDispatcher: CloseableCoroutineDispatcher,
) : AnalyticsRepository {
    override suspend fun loadAnalytics(): Result<Analytics, DataError> = withContext(Dispatchers.IO) {
        safeCall<AnalyticsDto> {
            httpClient.get("analytics")
        }.map {
            it.toModel()
        }
    }
}