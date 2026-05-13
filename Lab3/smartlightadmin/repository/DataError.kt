package ua.nure.smartlightadmin.repository

sealed interface DataError: Error {
    enum class Remote: DataError {
        REQUEST_TIMEOUT,
        TOO_MANY_REQUESTS,
        NO_INTERNET,
        SERVER,
        SERIALIZATION,
        UNKNOWN
    }

    data class ApiError(
        val message: String,
    ) : DataError
}

fun DataError.toErrorMessage() =
    when(this) {
        DataError.Remote.REQUEST_TIMEOUT -> "Timeout error"
        DataError.Remote.TOO_MANY_REQUESTS -> "Too many requests"
        DataError.Remote.NO_INTERNET -> "No internet"
        DataError.Remote.SERVER -> "Server error"
        DataError.Remote.SERIALIZATION -> "Serialization"
        DataError.Remote.UNKNOWN -> "Unknown"
        is DataError.ApiError -> this.message
    }