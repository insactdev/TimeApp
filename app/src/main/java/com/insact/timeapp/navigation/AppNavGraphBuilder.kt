package com.insact.timeapp.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.insact.timeapp.presentation.screens.list_screen.CapitalsScreen
import com.insact.timeapp.presentation.screens.list_screen.CapitalsViewModel
import com.insact.timeapp.presentation.screens.main_screen.MainScreen
import com.insact.timeapp.presentation.screens.main_screen.MainScreenViewModel
import com.insact.timeapp.utils.asStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

fun NavGraphBuilder.appNavGraphBuilder(navController: NavHostController) {
    mainScreen(navController)
    capitalsScreen(navController)
}

private fun NavGraphBuilder.mainScreen(navController: NavHostController) {
    composable<ScreenConfig.MainScreenRoute> {
        val viewModel: MainScreenViewModel = koinViewModel {
            parametersOf(navController)
        }
        MainScreen(
            state = viewModel.viewState.asStateWithLifecycle(),
            event = viewModel.event,
            onIntent = viewModel::handleIntent
        )
    }
}

private fun NavGraphBuilder.capitalsScreen(navController: NavHostController) {
    composable<ScreenConfig.CapitalsRoute> {
        val viewModel: CapitalsViewModel = koinViewModel {
            parametersOf(navController)
        }
        CapitalsScreen(
            state = viewModel.viewState.asStateWithLifecycle(),
            event = viewModel.event,
            onIntent = viewModel::handleIntent
        )
    }
}