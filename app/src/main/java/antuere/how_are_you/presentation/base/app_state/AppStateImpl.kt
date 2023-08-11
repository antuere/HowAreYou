package antuere.how_are_you.presentation.base.app_state

import androidx.activity.compose.BackHandler
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.hapticfeedback.HapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import antuere.domain.util.Constants
import antuere.how_are_you.LocalDarkThemeValue
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialogListener
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AppStateImpl(
    val navController: NavHostController,
    val snackbarHostState: SnackbarHostState,
    private val scope: CoroutineScope,
    val dialogListener: UIDialogListener,
    val appBarState: MutableState<AppBarState>,
    private val isDisableBackHandler: MutableState<Boolean>,
    val systemUiController: SystemUiController,
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
        val isDarkIcons = !LocalDarkThemeValue.current

        val isVisibleBottomBar = !appBarState.value.isVisibleBottomBar
        val colorNavBarColor =
            if (appBarState.value.isVisibleBottomBar) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background
        val colorStatusBar =
            if (appBarState.value.isVisibleTopBar) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.background

        LaunchedEffect(isVisibleBottomBar, colorNavBarColor) {
            systemUiController.setNavigationBarColor(
                color = colorNavBarColor,
                darkIcons = isDarkIcons,
            )
        }

        LaunchedEffect(appBarState.value.isVisibleTopBar, MaterialTheme.colorScheme.primary) {
            systemUiController.setStatusBarColor(
                color = colorStatusBar,
                darkIcons = isDarkIcons
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

@Composable
fun rememberAppState(
    navController: NavHostController = rememberNavController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    scope: CoroutineScope = rememberCoroutineScope(),
    dialogListener: UIDialogListener = remember {
        UIDialogListener()
    },
    appBarState: MutableState<AppBarState> = remember {
        mutableStateOf(AppBarState(isVisibleTopBar = false, isVisibleBottomBar = false))
    },
    isDisableBackHandler: MutableState<Boolean> = remember { mutableStateOf(true) },
    systemUiController: SystemUiController = rememberSystemUiController()
) = remember {
    AppStateImpl(
        navController = navController,
        snackbarHostState = snackbarHostState,
        scope = scope,
        appBarState = appBarState,
        dialogListener = dialogListener,
        isDisableBackHandler = isDisableBackHandler,
        systemUiController = systemUiController
    )
}