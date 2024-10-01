package com.insact.timeapp.domain.usecase

import com.insact.timeapp.domain.Repository
import com.insact.timeapp.domain.model.RequestResult

class GetCapitalsUseCase(private val repository: Repository) {

    suspend operator fun invoke() = when (val response = repository.loadCapitals()) {
        is RequestResult.Success -> {
            response.result.orEmpty()
                .filter {
                    it.contains(EUROPE)
                }
                .sorted()
                .take(5)
        }

        is RequestResult.Error -> {
            emptyList()
        }
    }

    companion object {
        private const val EUROPE = "Europe"
    }
}
