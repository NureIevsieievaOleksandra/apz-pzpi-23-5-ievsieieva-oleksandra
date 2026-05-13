package ua.nure.smartlightadmin.di

import org.koin.core.module.Module

expect val networkModule : Module

data class ApiErrorException(
    val apiError: ApiError,
    val httpStatus: Int,
    override val cause: Throwable? = null
) : Exception("API Error: ${apiError.message}", cause)

data class ApiError(
    val message: String,
)