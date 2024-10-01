package com.insact.timeapp.presentation.screens.list_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.insact.timeapp.R
import com.insact.timeapp.domain.usecase.GetCapitalsUseCase
import com.insact.timeapp.domain.usecase.GetSelectedCapitalUseCase
import com.insact.timeapp.domain.usecase.SetSelectedCapitalUseCase
import com.insact.timeapp.presentation.screens.list_screen.model.CapitalsEvent
import com.insact.timeapp.presentation.screens.list_screen.model.CapitalsIntent
import com.insact.timeapp.presentation.screens.list_screen.model.CapitalsViewState
import com.insact.timeapp.presentation.screens.list_screen.model.setLoading
import com.insact.timeapp.utils.NAVIGATION_CAPITAL_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CapitalsViewModel(
    private val navigation: NavHostController,
    private val getCapitalsUseCase: GetCapitalsUseCase,
    private val getSelectedCapitalUseCase: GetSelectedCapitalUseCase,
    private val setSelectedCapitalUseCase: SetSelectedCapitalUseCase
): ViewModel() {

    private val _viewState = MutableStateFlow(CapitalsViewState())
    val viewState = _viewState.asStateFlow()

    private val _event = MutableSharedFlow<CapitalsEvent>()
    val event = _event.asSharedFlow()

    init {
        loadCapitals()
    }

    fun handleIntent(intent: CapitalsIntent) {
        when (intent) {
            is CapitalsIntent.SelectCapital -> {
                selectCapital(intent.capital)
            }
        }
    }

    private fun loadCapitals() = viewModelScope.launch(Dispatchers.IO) {
        _viewState.update { it.setLoading(true) }

        val capitals = getCapitalsUseCase()
        val selectedCapital = getSelectedCapitalUseCase()
        _viewState.update {
            it.copy(
                capitals = capitals,
                selectedCapital = selectedCapital,
                loading = false
            )
        }

        if (capitals.isEmpty()) {
            setEvent(CapitalsEvent.ShowError(R.string.couldnt_load_capitals))
        }
    }

    private fun selectCapital(capital: String) = viewModelScope.launch(Dispatchers.IO) {
        setSelectedCapitalUseCase(capital)
        withContext(Dispatchers.Main) {
            goBack(capital)
        }
    }

    private fun goBack(capital: String) {
        navigation.previousBackStackEntry?.savedStateHandle?.set(NAVIGATION_CAPITAL_KEY, capital)
        navigation.popBackStack()
    }

    private fun setEvent(event: CapitalsEvent) {
        _event.tryEmit(event)
    }
}