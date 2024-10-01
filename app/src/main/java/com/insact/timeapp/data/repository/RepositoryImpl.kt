package com.insact.timeapp.data.repository

import com.insact.timeapp.data.SharedPrefs
import com.insact.timeapp.data.api.TimeServiceApi
import com.insact.timeapp.data.mapper.getRequestResult
import com.insact.timeapp.data.mapper.getRequestResultWithMapping
import com.insact.timeapp.data.mapper.toDomain
import com.insact.timeapp.domain.Repository
import com.insact.timeapp.domain.model.RequestResult
import com.insact.timeapp.domain.model.TimeZoneDomainModel

class RepositoryImpl(
    private val api: TimeServiceApi,
    private val sharedPrefs: SharedPrefs
): Repository {

    override suspend fun loadCapitals() =
        api.getCapitals().getRequestResult()

    override suspend fun getTime(capital: String): RequestResult<TimeZoneDomainModel> =
        api.getTimeZone(capital).getRequestResultWithMapping { it.toDomain() }

    override suspend fun getSelectedCapital() =
        sharedPrefs.getSelectedCapital()

    override suspend fun writeSelectedCapital(capital: String) =
        sharedPrefs.setSelectedCapital(capital)

}