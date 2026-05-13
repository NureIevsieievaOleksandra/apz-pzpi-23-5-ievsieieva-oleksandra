package ua.nure.smartlightadmin.repository.admin

import ua.nure.data.admin.BackupDto
import ua.nure.smartlightadmin.repository.DataError
import ua.nure.smartlightadmin.repository.Result

interface AdminRepository {
    suspend fun list(): Result<List<BackupDto>, DataError>
    suspend fun create(): Result<String, DataError>
    suspend fun restore(fileName: String): Result<Any, DataError>
    suspend fun delete(fileName: String): Result<String, DataError>
}