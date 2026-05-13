package ua.nure.smartlight.repository.lamp

import androidx.datastore.dataStore
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.nure.smartlight.db.entity.LampEntity
import ua.nure.smartlight.di.DbDeliveryDispatcher
import ua.nure.smartlight.repository.DataError
import ua.nure.smartlight.repository.Result
import ua.nure.smartlight.repository.db.DbRepository
import ua.nure.smartlight.repository.group.mapper.toEntit
import ua.nure.smartlight.repository.lamp.dto.LampDto
import ua.nure.smartlight.repository.lamp.mapper.toDto
import ua.nure.smartlight.repository.map
import ua.nure.smartlight.repository.onSuccess
import ua.nure.smartlight.repository.safeCall

class LampRepositoryImpl(
    private val httpClient: HttpClient,
    private val dbRepository: DbRepository,
    @DbDeliveryDispatcher private val dbDeliveryDispatcher: CloseableCoroutineDispatcher,
) : LampRepository {
    override suspend fun editLamp(lampEntity: LampEntity): Result<LampEntity, DataError> =
        withContext(Dispatchers.IO) {
            safeCall<LampDto> {
                if (lampEntity.lampId > 0) {
                    httpClient.put("lamp") {
                        setBody(
                            lampEntity.toDto()
                        )
                    }
                } else {
                    httpClient.post("lamp") {
                        setBody(lampEntity.toDto())
                    }
                }
            }.map {
                it.toEntit()
            }.onSuccess { lamp ->
                dbRepository.db.lampDao.insert(listOf(lamp))
            }
        }

    override suspend fun deleteLamp(lampId: Long): Result<Any, DataError> = withContext(
        Dispatchers.IO
    ) {
        safeCall<Any> {
            httpClient.delete("lamp/${lampId}") {

            }
        }.onSuccess {
            dbRepository.db.lampDao.delete(lampId = lampId)
        }
    }
}