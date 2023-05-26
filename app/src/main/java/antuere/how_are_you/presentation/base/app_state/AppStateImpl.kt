package antuere.how_are_you.presentation.base.app_state

import androidx.activity.compose.BackHandler
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.navigation.NavHostController
import antuere.domain.util.Constants
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialogListener
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.*


class AppStateImpl(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    private val scope: CoroutineScope,
    val dialogListener: UIDialogListener,
    val appBarState: MutableState<AppBarState>,
    private val isDisableBackHandler: MutableState<Boolean>,
) : AppState {

    override fun showDialog(dialog: UIDialog) {
        dialogListener.showDialog(dialog)
    }

    override fun showSnackbar(message: String, duration: Long) {
        val outerScope = CoroutineScope(Dispatchers.Default)
        outerScope.launch {
            val job = scope.launch {
                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Indefinite
                )
            }
            delay(duration)
            job.cancel()
        }
    }

    override fun dismissSnackbar() {
        snackbarHostState.currentSnackbarData?.dismiss()
    }

    override fun updateAppBar(newState: AppBarState) {
        appBarState.value = newState
    }

    override fun vibratePhone(hapticFeedback: HapticFeedback) {
        hapticFeedback.performHapticFeedback(HapticFeedbackType.LongPress)
    }

    override fun changeVisibilityBottomBar(isVisible: Boolean) {
        appBarState.value = appBarState.value.copy(isVisibleBottomBar = isVisible)
    }

    override fun changeVisibilityTopBar(isVisible: Boolean) {
        appBarState.value = appBarState.value.copy(isVisibleTopBar = isVisible)
    }

    override fun navigateUp() {
        if (!isDisableBackHandler.value) {
            navController.navigateUp()
        }
    }

    @Composable
    override fun SetupAppColors() {
        val systemUiController = rememberSystemUiController()
        val isUseDarkIcons = !appBarState.value.isVisibleBottomBar
        val colorNavBarColor =
            if (appBarState.value.isVisibleBottomBar) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background
        val colorStatusBar =
            if (appBarState.value.isVisibleTopBar) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background

        LaunchedEffect(isUseDarkIcons, colorNavBarColor) {
            systemUiController.setNavigationBarColor(
                color = colorNavBarColor,
                darkIcons = true,
            )
        }

        LaunchedEffect(appBarState.value.isVisibleTopBar) {
            systemUiController.setStatusBarColor(
                color = colorStatusBar,
                darkIcons = true
            )
        }
    }

    @Composable
    override fun DisableBackBtnWhileTransitionAnimate() {
        LaunchedEffect(Unit) {
            isDisableBackHandler.value = true
            delay(Constants.ANIM_DEFAULT_DURATION.toLong())
            isDisableBackHandler.value = false
        }

        BackHandler(enabled = isDisableBackHandler.value) {
            // Disable back handler while transition animation not finish
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberAppState(
    navController: NavHostController = rememberAnimatedNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
    dialogListener: UIDialogListener = remember {
        UIDialogListener()
    },
    appBarState: MutableState<AppBarState> = remember {
        mutableStateOf(AppBarState(isVisibleTopBar = false, isVisibleBottomBar = false))
    },
    isDisableBackHandler: MutableState<Boolean> = remember { mutableStateOf(true) },
) = remember {
    AppStateImpl(
        navController = navController,
        snackbarHostState = snackbarHostState,
        scope = scope,
        appBarState = appBarState,
        dialogListener = dialogListener,
        isDisableBackHandler = isDisableBackHandler
    )
}