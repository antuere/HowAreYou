package com.example.zeroapp.presentation.base.ui_compose_components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.*

class AppState(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    private val snackbarScope: CoroutineScope,
    var appBarState: MutableState<AppBarState>,
) {
    fun showSnackbar(message: String, duration: Long = 2000L) {
        val outerScope = CoroutineScope(Dispatchers.Default)
        outerScope.launch {
            val job = snackbarScope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Short
                )
            }
            delay(duration)
            job.cancel()
        }

    }

    fun dismissSnackbar() {
        snackbarHostState.currentSnackbarData?.dismiss()
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberAppState(
    navController: NavHostController = rememberAnimatedNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarScope: CoroutineScope = rememberCoroutineScope(),
    appBarState: MutableState<AppBarState> = remember {
        mutableStateOf(AppBarState())
    }
) = remember {
    mutableStateOf(
        AppState(
            navController = navController,
            snackbarHostState = snackbarHostState,
            snackbarScope = snackbarScope,
            appBarState = appBarState
        )
    )
}