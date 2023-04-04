package antuere.how_are_you.presentation.base.app_state

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.navigation.NavHostController
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialogListener
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.*


class AppStateImpl(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    private val snackbarScope: CoroutineScope,
    val dialogListener: UIDialogListener,
    var appBarState: MutableState<AppBarState>,
) : AppState {

    override fun showDialog(dialog: UIDialog) {
        dialogListener.showDialog(dialog)
    }

    override fun showSnackbar(message: String, duration: Long) {
        val outerScope = CoroutineScope(Dispatchers.Default)
        outerScope.launch {
            val job = snackbarScope.launch {
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
        navController.navigateUp()
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
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberAppState(
    navController: NavHostController = rememberAnimatedNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarScope: CoroutineScope = rememberCoroutineScope(),
    dialogListener: UIDialogListener = remember {
        UIDialogListener()
    },
    appBarState: MutableState<AppBarState> = remember {
        mutableStateOf(AppBarState())
    },
) = remember {
    AppStateImpl(
        navController = navController,
        snackbarHostState = snackbarHostState,
        snackbarScope = snackbarScope,
        appBarState = appBarState,
        dialogListener = dialogListener
    )
}