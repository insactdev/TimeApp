package com.insact.timeapp.domain.usecase

import com.insact.timeapp.domain.Repository

class SetSelectedCapitalUseCase(private val repository: Repository) {

    suspend operator fun invoke(capital: String) =
        repository.writeSelectedCapital(capital)
}