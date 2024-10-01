package com.insact.timeapp.domain.usecase

import com.insact.timeapp.domain.Repository

class GetSelectedCapitalUseCase(private val repository: Repository) {

    suspend operator fun invoke() =
        repository.getSelectedCapital()
}