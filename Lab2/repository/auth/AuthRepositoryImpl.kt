package ua.nure.smartlight.repository.auth

import ua.nure.smartlight.repository.auth.dto.SignInResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.CloseableCoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.nure.smartlight.di.DbDeliveryDispatcher
import ua.nure.smartlight.repository.DataError
import ua.nure.smartlight.repository.Result
import ua.nure.smartlight.repository.auth.dto.SignInRequest
import ua.nure.smartlight.repository.db.DbRepository
import ua.nure.smartlight.repository.onSuccess
import ua.nure.smartlight.repository.safeCall
import ua.nure.smartlight.repository.token.TokenRepository

class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val dbRepository: DbRepository,
    private val tokenRepository: TokenRepository,
    @DbDeliveryDispatcher private val dbDeliveryDispatcher: CloseableCoroutineDispatcher,
) : AuthRepository {
    override suspend fun signIn(
        username: String,
        password: String
    ): Result<SignInResponse, DataError> = withContext(Dispatchers.IO) {
        safeCall<SignInResponse> {
            httpClient.post("signIn") {
                setBody(
                    SignInRequest(
                        username = username,
                        password = password
                    )
                )
            }
        }.onSuccess { user ->
            tokenRepository.setToken(user.token)
            tokenRepository.setUserName(user.userName)
            tokenRepository.setRole(user.role)
            tokenRepository.setUserId(user.userId ?: -1L)
        }
    }
}