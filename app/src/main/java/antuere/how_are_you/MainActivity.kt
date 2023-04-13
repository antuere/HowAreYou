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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.viewbinding.BuildConfig
import antuere.domain.repository.SettingsRepository
import antuere.domain.util.Constants
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
import antuere.how_are_you.presentation.screens.add_day.AddDayScreen
import antuere.how_are_you.presentation.screens.cats.CatsScreen
import antuere.how_are_you.presentation.screens.detail.DetailScreen
import antuere.how_are_you.presentation.screens.favorites.FavoritesScreen
import antuere.how_are_you.presentation.screens.help_for_you.HelpForYouScreen
import antuere.how_are_you.presentation.screens.helplines.HelplinesScreen
import antuere.how_are_you.presentation.screens.history.HistoryScreen
import antuere.how_are_you.presentation.screens.home.HomeScreen
import antuere.how_are_you.presentation.screens.home.HomeViewModel
import antuere.how_are_you.presentation.screens.mental_tips.MentalTipsScreen
import antuere.how_are_you.presentation.screens.mental_tips_categories.MentalTipsCategoriesScreen
import antuere.how_are_you.presentation.screens.reset_password.ResetPasswordScreen
import antuere.how_are_you.presentation.screens.secure_entry.SecureEntryScreen
import antuere.how_are_you.presentation.screens.settings.SettingsScreen
import antuere.how_are_you.presentation.screens.sign_in_methods.SignInMethodsScreen
import antuere.how_are_you.presentation.screens.sign_in_with_email.SignInEmailScreen
import antuere.how_are_you.presentation.screens.sign_up_with_email.SignUpEmailScreen
import antuere.how_are_you.util.ComposableLifecycle
import antuere.how_are_you.util.extensions.toStable
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

val LocalAppState = compositionLocalOf<AppState> { error("App state not set yet!") }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val homeViewModel by viewModels<HomeViewModel>()

    private val dateChangeReceiver by lazy {
        DateChangeReceiver(dateChanged = {
            homeViewModel.dayChanged()
        })
    }

    @Inject
    lateinit var settingsRepository: SettingsRepository

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var isEnabledPin = false
        lifecycleScope.launch(Dispatchers.IO) {
            settingsRepository.getPinSetting().collectLatest { isEnabledPin = it }
        }

        Timber.plant(Timber.DebugTree())
        WindowCompat.setDecorFitsSystemWindows(window, false)
        registerReceiver(dateChangeReceiver, IntentFilter(Intent.ACTION_DATE_CHANGED))

        installSplashScreen().apply {
            if (BuildConfig.BUILD_TYPE != "benchmark") {
                setKeepOnScreenCondition {
                    homeViewModel.isShowSplash.value
                }
            }
        }

        setContent {
            HowAreYouTheme {
                val appState: AppStateImpl = rememberAppState()
                val appBarState by appState.appBarState
                val navController = appState.navController

                var timeWhenAppClosed by rememberSaveable { mutableStateOf(0L) }

                val startDestination = remember {
                    if (isEnabledPin) Screen.SecureEntry.route else Screen.Home.route
                }

                appState.dialogListener.SetupDialogListener()
                appState.SetupAppColors()

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
                            titleId = appBarState.titleId,
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
                            startDestination = startDestination
                        ) {
                            setupMainNavGraph(
                                navController = navController,
                                homeViewModel = homeViewModel
                            )
                        }

                        ComposableLifecycle { _, event ->
                            if (event == Lifecycle.Event.ON_STOP) {
                                timeWhenAppClosed = System.currentTimeMillis()
                            }
                            if (event == Lifecycle.Event.ON_RESUME) {
                                if (isEnabledPin && TimeUtility.isNeedLockApp(timeWhenAppClosed)) {
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
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.setupMainNavGraph(
    navController: NavController,
    homeViewModel: HomeViewModel,
) {
    composable(
        route = Screen.Home.route,
        enterTransition = {
            when (initialState.destination.route) {
                Screen.Settings.route, Screen.History.route -> materialFadeThroughIn()
                else -> materialSharedAxisZIn(forward = false)
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Screen.Settings.route, Screen.History.route -> materialFadeThroughOut()
                else -> materialSharedAxisZOut(forward = true)
            }
        },
    ) {
        HomeScreen(
            onNavigateToMentalTips = navController.navigateToMentalTipsCategories(),
            onNavigateToFavorites = navController.navigateToFavorites(),
            onNavigateToHelpForYou = navController.navigateToHelpForYou(),
            onNavigateToCats = navController.navigateToCats(),
            onNavigateToDetail = navController.navigateToDayDetail(),
            onNavigateToAddDay = navController.navigateToAddDay(),
            viewModel = { homeViewModel }.toStable()
        )
    }

    composable(
        route = Screen.Favorites.route,
        enterTransition = { materialSharedAxisZIn(forward = true) },
        exitTransition = { materialSharedAxisZOut(forward = false) }
    ) {
        FavoritesScreen(onNavigateToDetail = navController.navigateToDayDetail())
    }

    composable(
        route = Screen.Cats.route,
        enterTransition = { materialSharedAxisZIn(forward = true) },
        exitTransition = { materialSharedAxisZOut(forward = false) }
    ) {
        CatsScreen()
    }

    composable(
        route = Screen.HelpForYou.route,
        enterTransition = { materialSharedAxisZIn(forward = true) },
        exitTransition = { materialSharedAxisZOut(forward = false) },
        popEnterTransition = { materialSharedAxisXIn(forward = false) }
    ) {
        HelpForYouScreen(onNavigateToHelplines = navController.navigateToHelplines())
    }

    composable(
        route = Screen.Helplines.route,
        enterTransition = { materialSharedAxisXIn(forward = true) },
        exitTransition = { materialSharedAxisXOut(forward = false) },
    ) {
        HelplinesScreen()
    }

    composable(
        route = Screen.MentalTipsCategories.route,
        enterTransition = { materialSharedAxisZIn(forward = true) },
        exitTransition = { materialSharedAxisZOut(forward = false) },
        popEnterTransition = { materialSharedAxisXIn(forward = false) }
    ) {
        MentalTipsCategoriesScreen(onNavigateToMentalTips = navController.navigateToMentalTips())
    }

    composable(
        route = Screen.MentalTips.route + "/{${Constants.CATEGORY_KEY}}",
        arguments = listOf(navArgument(Constants.CATEGORY_KEY) {
            type = NavType.StringType
        }),
        enterTransition = { materialSharedAxisXIn(forward = true) },
        exitTransition = { materialSharedAxisXOut(forward = false) },
    ) {
        MentalTipsScreen()
    }

    composable(
        route = Screen.AddDay.route,
        enterTransition = { materialSharedAxisZIn(forward = true) },
        exitTransition = { materialSharedAxisZOut(forward = false) }
    ) {
        AddDayScreen()
    }

    composable(
        route = Screen.History.route,
        enterTransition = {
            when (initialState.destination.route) {
                Screen.Settings.route, Screen.Home.route -> materialFadeThroughIn()
                else -> materialSharedAxisZIn(forward = false)
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Screen.Settings.route, Screen.Home.route -> materialFadeThroughOut()
                else -> materialSharedAxisZOut(forward = true)
            }
        },
    ) {
        HistoryScreen(onNavigateToDetail = navController.navigateToDayDetail())
    }

    composable(
        route = Screen.Detail.route + "/{${Constants.DAY_ID_KEY}}",
        arguments = listOf(navArgument(Constants.DAY_ID_KEY) {
            type = NavType.LongType
        }),
        enterTransition = { materialSharedAxisZIn(forward = true) },
        exitTransition = { materialSharedAxisZOut(forward = false) }
    ) {
        DetailScreen()
    }

    composable(
        route = Screen.Settings.route,
        enterTransition = {
            when (initialState.destination.route) {
                Screen.History.route, Screen.Home.route -> materialFadeThroughIn()
                else -> materialSharedAxisZIn(forward = false)
            }
        },
        exitTransition = {
            when (targetState.destination.route) {
                Screen.History.route, Screen.Home.route -> materialFadeThroughOut()
                else -> materialSharedAxisZOut(forward = true)
            }
        }
    ) {
        SettingsScreen(onNavigateSignIn = navController.navigateToSignIn())
    }

    composable(
        route = Screen.SignInMethods.route,
        enterTransition = { materialSharedAxisZIn(forward = true) },
        exitTransition = { materialSharedAxisZOut(forward = false) }
    ) {
        SignInMethodsScreen(onNavigateSignInEmail = navController.navigateToSignInEmail())
    }

    composable(
        route = Screen.SignInWithEmail.route,
        enterTransition = { materialSharedAxisXIn(forward = true) },
        exitTransition = {
            when (targetState.destination.route) {
                Screen.Settings.route -> materialSharedAxisZOut(forward = false)
                else -> materialSharedAxisXOut(forward = false)
            }
        },
        popEnterTransition = { materialSharedAxisXIn(forward = false) }
    ) {
        SignInEmailScreen(
            onNavigateSettings = navController.popBackStackToSettings(),
            onNavigateSignUp = navController.navigateToSignUpEmail(),
            onNavigateResetPassword = navController.navigateToResetPassword()
        )
    }

    composable(
        route = Screen.SignUpWithEmail.route,
        enterTransition = { materialSharedAxisXIn(forward = true) },
        exitTransition = {
            when (targetState.destination.route) {
                Screen.Settings.route -> materialSharedAxisZOut(forward = false)
                else -> materialSharedAxisXOut(forward = false)
            }
        },
    ) {
        SignUpEmailScreen(onNavigateSettings = navController.popBackStackToSettings())
    }

    composable(
        route = Screen.ResetPassEmail.route,
        enterTransition = { materialSharedAxisXIn(forward = true) },
        exitTransition = { materialSharedAxisXOut(forward = false) }
    ) {
        ResetPasswordScreen()
    }

    composable(
        route = Screen.SecureEntry.route,
        enterTransition = { materialSharedAxisZIn(forward = true) },
        exitTransition = { materialSharedAxisZOut(forward = false) }
    ) {
        SecureEntryScreen(onNavigateHomeScreen = navController.navigateToHome())
    }
}

