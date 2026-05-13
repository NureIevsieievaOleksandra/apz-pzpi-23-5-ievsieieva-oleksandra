package ua.nure.smartlight.repository.lamp

import ua.nure.smartlight.db.entity.LampEntity
import ua.nure.smartlight.repository.DataError
import ua.nure.smartlight.repository.Result

interface LampRepository {
    suspend fun editLamp(lampEntity: LampEntity): Result<LampEntity, DataError>
    suspend fun deleteLamp(lampId: Long): Result<Any, DataError>
}