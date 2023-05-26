package antuere.how_are_you

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Lifecycle
import antuere.domain.util.TimeUtility
import antuere.how_are_you.broadcastReceivers.DateChangeReceiver
import antuere.how_are_you.presentation.base.app_state.AppState
import antuere.how_are_you.presentation.base.app_state.AppStateImpl
import antuere.how_are_you.presentation.base.app_state.rememberAppState
import antuere.how_are_you.presentation.base.navigation.*
import antuere.how_are_you.presentation.base.ui_animations.*
import antuere.how_are_you.presentation.base.ui_compose_components.bottom_nav_bar.DefaultBottomNavBar
import antuere.how_are_you.presentation.base.ui_compose_components.snackbar.DefaultSnackbar
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.DefaultTopBar
import antuere.how_are_you.presentation.base.ui_theme.HowAreYouTheme
import antuere.how_are_you.presentation.screens.home.HomeViewModel
import antuere.how_are_you.presentation.screens.splash.SplashViewModel
import antuere.how_are_you.util.ComposableLifecycle
import com.google.accompanist.navigation.animation.AnimatedNavHost
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*

val LocalAppState = compositionLocalOf<AppState> { error("App state not set yet!") }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val splashViewModel by viewModels<SplashViewModel>()
    private val homeViewModel by viewModels<HomeViewModel>()

    private val dateChangeReceiver by lazy {
        DateChangeReceiver(dateChanged = {
            homeViewModel.dayChanged()
        })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Timber.plant(Timber.DebugTree())
        WindowCompat.setDecorFitsSystemWindows(window, false)
        registerReceiver(dateChangeReceiver, IntentFilter(Intent.ACTION_DATE_CHANGED))

        installSplashScreen().apply {
            if (BuildConfig.BUILD_TYPE != "benchmark") {
                setKeepOnScreenCondition {
                    splashViewModel.isShowSplash.value
                }
            }
        }

        setContent {
            HowAreYouTheme {
                val isEnablePin by splashViewModel.isEnablePin.collectAsState()
                val startScreen by splashViewModel.startScreen.collectAsState()
                val isShowSplash by splashViewModel.isShowSplash.collectAsState()
                val appState: AppStateImpl = rememberAppState()

                appState.dialogListener.SetupDialogListener()
                appState.SetupAppColors()

                if (!isShowSplash) {
                    RenderUI(
                        startScreen = { startScreen },
                        isEnablePin = { isEnablePin },
                        appState = appState
                    )
                }
            }
        }
    }

    @Composable
    @OptIn(ExperimentalAnimationApi::class)
    private fun RenderUI(
        startScreen: () -> Screen,
        isEnablePin: () -> Boolean,
        appState: AppStateImpl,
    ) {
        val appBarState by appState.appBarState
        val navController = appState.navController
        var timeWhenAppClosed by rememberSaveable { mutableStateOf(0L) }

        Scaffold(
            snackbarHost = {
                SnackbarHost(appState.snackbarHostState) { data ->
                    DefaultSnackbar(text = data.visuals.message)
                }
            },
            bottomBar = {
                DefaultBottomNavBar(
                    navController = navController,
                    isVisible = appBarState.isVisibleBottomBar
                )
            },
            topBar = {
                DefaultTopBar(
                    title = appBarState.topBarTitle,
                    topBarType = appBarState.topBarType,
                    isVisible = appBarState.isVisibleTopBar,
                    navigationIcon = appBarState.navigationIcon,
                    navigationOnClick = appBarState.onClickNavigationBtn,
                    actions = appBarState.actions
                )
            }) { innerPadding ->
            CompositionLocalProvider(LocalAppState provides appState) {
                AnimatedNavHost(
                    modifier = Modifier
                        .systemBarsPadding()
                        .navigationBarsPadding(),
                    navController = navController,
                    startDestination = startScreen().route
                ) {
                    initRootNavGraph(
                        navController = navController,
                        homeViewModelLambda = { homeViewModel }
                    )
                }

                ComposableLifecycle { _, event ->
                    if (event == Lifecycle.Event.ON_STOP) {
                        timeWhenAppClosed = System.currentTimeMillis()
                    }
                    if (event == Lifecycle.Event.ON_RESUME) {
                        if (isEnablePin() && TimeUtility.isNeedLockApp(timeWhenAppClosed)) {
                            navController.navigateToSecure()
                        }
                        timeWhenAppClosed = 0
                    }
                    if (event == Lifecycle.Event.ON_DESTROY) {
                        unregisterReceiver(dateChangeReceiver)
                    }
                }
            }
        }
    }
}
