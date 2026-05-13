package ua.nure.smartlightadmin.repository.auth

import ua.nure.smartlightadmin.repository.DataError
import ua.nure.smartlightadmin.repository.Result
import ua.nure.smartlightadmin.repository.auth.dto.SignInResponse

interface AuthRepository {
    suspend fun signIn(username: String, password: String): Result<SignInResponse, DataError>
}