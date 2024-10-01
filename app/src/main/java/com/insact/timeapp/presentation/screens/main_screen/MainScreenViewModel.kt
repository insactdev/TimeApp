package com.insact.timeapp.presentation.screens.main_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.insact.timeapp.domain.usecase.GetCapitalsUseCase
import com.insact.timeapp.domain.usecase.GetSelectedCapitalUseCase
import com.insact.timeapp.domain.usecase.GetTimeUseCase
import com.insact.timeapp.domain.usecase.SetSelectedCapitalUseCase
import com.insact.timeapp.navigation.ScreenConfig
import com.insact.timeapp.presentation.screens.main_screen.model.MainEvent
import com.insact.timeapp.presentation.screens.main_screen.model.MainIntent
import com.insact.timeapp.presentation.screens.main_screen.model.MainState
import com.insact.timeapp.presentation.screens.main_screen.model.setLoading
import com.insact.timeapp.utils.EMPTY_STRING
import com.insact.timeapp.utils.NAVIGATION_CAPITAL_KEY
import com.insact.timeapp.utils.getValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainScreenViewModel(
    private val navigation: NavHostController,
    private val getCapitalsUseCase: GetCapitalsUseCase,
    private val getSelectedCapitalUseCase: GetSelectedCapitalUseCase,
    private val setSelectedCapitalUseCase: SetSelectedCapitalUseCase,
    private val getTimeUseCase: GetTimeUseCase,
) : ViewModel() {

    private val _viewState = MutableStateFlow(MainState())
    val viewState = _viewState.asStateFlow()

    private val _event = MutableSharedFlow<MainEvent>()
    val event = _event.asSharedFlow()

    private var job: Job? = null

    init {
        job = startFlow()
        observeSavedState()
    }

    fun handleIntent(intent: MainIntent) {
        when (intent) {
            is MainIntent.GoToSelectCapital -> {
                navigation.navigate(ScreenConfig.CapitalsRoute)
            }
        }
    }

    private fun startFlow(
        capitalInput: String = EMPTY_STRING
    ) = viewModelScope.launch(Dispatchers.IO) {
            _viewState.update { it.setLoading(true) }

            val capital = if (capitalInput.isEmpty()) {
                getSelectedCapitalUseCase().ifEmpty {
                    val cap = getCapitalsUseCase().first()
                    setSelectedCapitalUseCase(cap)
                    return@ifEmpty cap
                }
            } else {
                capitalInput
            }

            val flow = getTimeUseCase(capital)

            flow.cancellable()
                .catch {
                    _viewState.update { state -> state.setLoading(false) }
                    if (!it.message.isNullOrEmpty()) {
                        setEvent(MainEvent.ShowError(it.message.orEmpty()))
                    }
                }

                .collectLatest {
                    _viewState.update { state ->
                        state.copy(
                            capital = capital,
                            timeString = it,
                            loading = false
                        )
                    }
                }
        }

    private fun setEvent(event: MainEvent) {
        _event.tryEmit(event)
    }

    private fun observeSavedState() = viewModelScope.launch {
        navigation.currentBackStackEntryFlow.collect {
            it.savedStateHandle.getValue<String>(NAVIGATION_CAPITAL_KEY) { capital ->
                job?.cancel()
                job = startFlow(capital)
            }
        }
    }
}