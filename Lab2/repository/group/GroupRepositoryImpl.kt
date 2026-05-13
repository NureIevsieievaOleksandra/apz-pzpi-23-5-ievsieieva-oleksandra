package ua.nure.smartlight.repository.group

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import ua.nure.smartlight.db.dao.GroupDao
import ua.nure.smartlight.db.entity.Group
import ua.nure.smartlight.db.entity.GroupEntity
import ua.nure.smartlight.di.DbDeliveryDispatcher
import ua.nure.smartlight.repository.DataError
import ua.nure.smartlight.repository.Result
import ua.nure.smartlight.repository.db.DbRepository
import ua.nure.smartlight.repository.group.dto.CreateGroupRequest
import ua.nure.smartlight.repository.group.dto.GroupDto
import ua.nure.smartlight.repository.group.mapper.toEntit
import ua.nure.smartlight.repository.group.mapper.toEntity
import ua.nure.smartlight.repository.map
import ua.nure.smartlight.repository.onSuccess
import ua.nure.smartlight.repository.safeCall
import kotlin.math.exp

class GroupRepositoryImpl(
    private val httpClient: HttpClient,
    private val dbRepository: DbRepository,
    @DbDeliveryDispatcher private val dbDeliveryDispatcher: CloseableCoroutineDispatcher,
) : GroupRepository {
    private val TAG by lazy { GroupRepositoryImpl::class.simpleName }

    override suspend fun load(): Unit = withContext(Dispatchers.IO) {
        safeCall<List<GroupDto>> {
            httpClient.get("group") {

            }
        }.onSuccess { list ->
            dbRepository.db.groupDao.insert(
                list.map { it.toEntity() }
            )
            dbRepository.db.lampDao.insert(
                list.flatMap { it.lamps }.map { it.toEntit() }
            )
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun get(): Flow<List<Group>> =
        dbRepository.dbFlow.flatMapLatest {
            db -> db.groupDao.get()
        }.catch { ex -> ex.printStackTrace() }
            .flowOn(dbDeliveryDispatcher)

    override suspend fun create(
        title: String,
        description: String
    ): Result<GroupEntity, DataError> = withContext(Dispatchers.IO) {
        safeCall<GroupDto> {
            httpClient.post("group") {
                setBody(
                    CreateGroupRequest(
                        name = title,
                        description = description
                    )
                )
            }
        }.map {
            it.toEntity()
        }.onSuccess { newGroup ->
            dbRepository.db.groupDao.insert(listOf(newGroup))
        }
    }

    override suspend fun edit(
        groupId: Long,
        title: String,
        description: String
    ): Result<GroupEntity, DataError>  = withContext(Dispatchers.IO) {
        safeCall<GroupDto> {
            httpClient.put("group") {
                setBody(
                    CreateGroupRequest(
                        groupId = groupId,
                        name = title,
                        description = description
                    )
                )
            }
        }.map {
            it.toEntity()
        }.onSuccess { savedGroup ->
            dbRepository.db.groupDao.insert(listOf(savedGroup))
        }
    }

    override suspend fun delete(groupId: Long): Result<Any, DataError> = withContext(Dispatchers.IO) {
        safeCall<Any> {
            httpClient.delete("group/$groupId") {

            }
        }.onSuccess {
            dbRepository.db.groupDao.deleteById(groupId = groupId)
        }
    }
}