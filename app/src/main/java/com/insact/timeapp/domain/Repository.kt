package com.insact.timeapp.domain

import com.insact.timeapp.domain.model.RequestResult
import com.insact.timeapp.domain.model.TimeZoneDomainModel

interface Repository {
    suspend fun loadCapitals(): RequestResult<List<String>>

    suspend fun getSelectedCapital(): String

    suspend fun writeSelectedCapital(capital: String)

    suspend fun getTime(capital: String): RequestResult<TimeZoneDomainModel>
}