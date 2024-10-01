package com.insact.timeapp.domain.usecase

import android.util.Log
import com.insact.timeapp.domain.Repository
import com.insact.timeapp.domain.model.RequestResult
import com.insact.timeapp.domain.model.TimeZoneDomainModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class GetTimeUseCase(private val repository: Repository) {

    private val datetimePattern = "yyyy-MM-dd'T'HH:mm:ss"
    private val timePattern = "HH:mm:ss"
    private val TIME_MODEL_EMPTY_ERROR = "Не удалось получить время"
    private val COULDNT_PARSE_TIME = "Не удалось обработать время"


    suspend operator fun invoke(capital: String): Flow<String> {
        return when (val timeModel = repository.getTime(capital)) {
            is RequestResult.Success -> {
                createTimerFlow(timeModel.result)
            }

            is RequestResult.Error -> {
                flow {
                    throw Exception(timeModel.message)
                }
            }
        }
    }

    private suspend fun createTimerFlow(timeModel: TimeZoneDomainModel?) = flow {
        timeModel ?: throw Exception(TIME_MODEL_EMPTY_ERROR)
        val datetimeFormat = SimpleDateFormat(datetimePattern, Locale.getDefault())
        val timeFormat = SimpleDateFormat(timePattern, Locale.getDefault())
        datetimeFormat.parse(timeModel.time)?.let {
            val diff = System.currentTimeMillis() - it.time
            while (currentCoroutineContext().isActive) {
                Log.e("adasdasda", "${currentCoroutineContext()}, emit")
                emit(
                    timeFormat.format(
                        Date(
                            System.currentTimeMillis() - diff
                        )
                    )
                )
            }

        } ?: throw Exception(COULDNT_PARSE_TIME)
    }
        .onEach { delay(1000) }
        .flowOn(Dispatchers.IO)
}