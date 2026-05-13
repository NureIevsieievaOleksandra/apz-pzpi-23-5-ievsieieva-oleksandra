package ua.nure.smartlightadmin.repository.admin

import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.nure.data.admin.BackupDto
import ua.nure.data.admin.dto.BackupRequest
import ua.nure.smartlightadmin.repository.DataError
import ua.nure.smartlightadmin.repository.Result
import ua.nure.smartlightadmin.repository.auth.AuthRepository
import ua.nure.smartlightadmin.repository.safeCall

class AdminRepositoryImpl(
    private val httpClient: HttpClient
) : AdminRepository {
    override suspend fun list(): Result<List<BackupDto>, DataError> =
        withContext(Dispatchers.Default) {
            safeCall<List<BackupDto>> {
                httpClient.get("admin/backup") {

                }
            }
        }

    override suspend fun create(): Result<String, DataError> = withContext(Dispatchers.Default) {
        safeCall<String> {
            httpClient.post("admin/backup") {

            }
        }
    }

    override suspend fun restore(fileName: String): Result<Any, DataError> =
        withContext(Dispatchers.Default) {
            safeCall<Any> {
                httpClient.put {
                    setBody(
                        BackupRequest(
                            fileName = fileName
                        )
                    )
                }
            }
        }

    override suspend fun delete(fileName: String): Result<String, DataError> =
        withContext(
            Dispatchers.Default
        ) {
            safeCall<String> {
                httpClient.delete("admin/backup/$fileName")
            }
        }
}