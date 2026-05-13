package ua.nure.smartlight.repository.auth

import ua.nure.smartlight.repository.auth.dto.SignInResponse
import ua.nure.smartlight.repository.DataError
import ua.nure.smartlight.repository.Result

interface AuthRepository {
    suspend fun signIn(username: String, password: String): Result<SignInResponse, DataError>
}