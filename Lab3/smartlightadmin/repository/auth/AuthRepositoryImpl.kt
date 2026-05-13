package ua.nure.smartlightadmin.repository.auth

import ua.nure.smartlightadmin.repository.auth.dto.SignInResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ua.nure.smartlightadmin.repository.auth.dto.SignInRequest
import ua.nure.smartlightadmin.repository.DataError
import ua.nure.smartlightadmin.repository.onSuccess
import ua.nure.smartlightadmin.repository.safeCall
import ua.nure.smartlightadmin.repository.token.TokenRepository
import ua.nure.smartlightadmin.repository.Result


class AuthRepositoryImpl(
    private val httpClient: HttpClient,
    private val tokenRepository: TokenRepository,
) : AuthRepository {
    override suspend fun signIn(
        username: String,
        password: String
    ): Result<SignInResponse, DataError> = withContext(Dispatchers.Default) {
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