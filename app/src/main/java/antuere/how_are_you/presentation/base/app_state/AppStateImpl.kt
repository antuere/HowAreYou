package antuere.how_are_you.presentation.base.app_state

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialogListener
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
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

    override fun vibratePhone(context: Context, duration: Long) {
        val vib = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                context.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            @Suppress("DEPRECATION")
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vib.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vib.vibrate(duration)
        }
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