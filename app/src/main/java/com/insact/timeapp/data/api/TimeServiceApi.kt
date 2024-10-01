package com.insact.timeapp.data.api

import com.insact.timeapp.data.dto.TimeZoneDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TimeServiceApi {

    @GET("api/timezone/availabletimezones")
    suspend fun getCapitals(): Response<List<String>>

    @GET("api/timezone/zone")
    suspend fun getTimeZone(
        @Query("timeZone") capital: String
    ): Response<TimeZoneDto>
}