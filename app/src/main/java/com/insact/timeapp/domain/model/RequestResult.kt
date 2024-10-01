package com.insact.timeapp.domain.model

sealed class RequestResult<out T: Any?> {
    data class Success<T>(val result: T?): RequestResult<T>()
    data class Error<T>(val code: Int, val message: String): RequestResult<T>()
}