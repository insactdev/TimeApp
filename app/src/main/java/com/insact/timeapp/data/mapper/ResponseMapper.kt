package com.insact.timeapp.data.mapper

import com.insact.timeapp.domain.model.RequestResult
import retrofit2.Response


const val HTTP_200 = 200

fun <T> Response<T>.getRequestResult(): RequestResult<T> = try {
    when (code()) {
        HTTP_200 -> RequestResult.Success(body())
        else -> RequestResult.Error(code = code(), message = message())
    }
} catch (e: Exception) {
    RequestResult.Error(code = code(), message = message())
}

inline fun <T, R> Response<T>.getRequestResultWithMapping(map: (T?) -> R): RequestResult<R> =
    try {
        when (code()) {
            HTTP_200 -> RequestResult.Success(map(body()))
            else -> RequestResult.Error(code = code(), message = message())
        }
    } catch (e: Exception) {
        RequestResult.Error(code = code(), message = message())
    }
