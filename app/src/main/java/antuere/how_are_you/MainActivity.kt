package antuere.how_are_you

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.viewbinding.BuildConfig
import antuere.domain.dto.Settings
import antuere.domain.repository.SettingsRepository
import antuere.domain.util.Constants
import antuere.how_are_you.presentation.add_day.AddDayScreen
import antuere.how_are_you.presentation.base.app_state.AppState
import antuere.how_are_you.presentation.base.app_state.AppStateImpl
import antuere.how_are_you.presentation.base.app_state.rememberAppState
import antuere.how_are_you.presentation.base.navigation.*
import antuere.how_are_you.presentation.base.ui_animations.materialFadeThroughIn
import antuere.how_are_you.presentation.base.ui_animations.materialFadeThroughOut
import antuere.how_are_you.presentation.base.ui_animations.materialSlideIn
import antuere.how_are_you.presentation.base.ui_animations.materialSlideOut
import antuere.how_are_you.presentation.base.ui_compose_components.bottom_nav_bar.DefaultBottomNavBar
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.DefaultTopBar
import antuere.how_are_you.presentation.base.ui_theme.HowAreYouTheme
import antuere.how_are_you.presentation.cats.CatsScreen
import antuere.how_are_you.presentation.detail.DetailScreen
import antuere.how_are_you.presentation.favorites.FavoritesScreen
import antuere.how_are_you.presentation.help_for_you.HelpForYouScreen
import antuere.how_are_you.presentation.helplines.HelplinesScreen
import antuere.how_are_you.presentation.history.HistoryScreen
import antuere.how_are_you.presentation.home.HomeScreen
import antuere.how_are_you.presentation.home.HomeViewModel
import antuere.how_are_you.presentation.mental_tips.MentalTipsScreen
import antuere.how_are_you.presentation.mental_tips_categories.MentalTipsCategoriesScreen
import antuere.how_are_you.presentation.reset_password.ResetPasswordScreen
import antuere.how_are_you.presentation.secure_entry.SecureEntryScreen
import antuere.how_are_you.presentation.settings.SettingsScreen
import antuere.how_are_you.presentation.sign_in_methods.SignInMethodsScreen
import antuere.how_are_you.presentation.sign_in_with_email.SignInEmailScreen
import antuere.how_are_you.presentation.sign_up_with_email.SignUpEmailScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

val LocalAppState = compositionLocalOf<AppState> { error("App state not set yet!") }

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val homeViewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var settingsRepository: SettingsRepository

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        WindowCompat.setDecorFitsSystemWindows(window, false)
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

        var settings: Settings? = null

        val job = lifecycleScope.launch(Dispatchers.IO) {
            settings = settingsRepository.getAllSettings().first()
        }

        installSplashScreen().apply {
            if (BuildConfig.BUILD_TYPE != "benchmark") {
                setKeepOnScreenCondition {
                    homeViewModel.isShowSplash.value
                }
            }
        }

        var startDestination = Screen.SecureEntry.route
        lifecycleScope.launch(Dispatchers.Main) {
            job.join()
            if (!settings!!.isPinCodeEnabled && !settings!!.isBiometricEnabled) {
                startDestination = Screen.Home.route
            }
        }

        setContent {
            HowAreYouTheme {
                Timber.i("MVI error test : composed in activity")

                val appState: AppStateImpl = rememberAppState()
                appState.dialogListener.SetupDialogListener()
                val appBarState by appState.appBarState

                val navController = remember { appState.navController }
                val getHomeViewModel: () -> HomeViewModel = remember { { homeViewModel } }

                val systemUiController = rememberSystemUiController()
                val isUseDarkIcons =
                    !(isSystemInDarkTheme() || (!isSystemInDarkTheme() && appBarState.isVisibleBottomBar))
                val colorNavBarColor =
                    if (appBarState.isVisibleBottomBar) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary

                LaunchedEffect(isUseDarkIcons, colorNavBarColor) {
                    systemUiController.setNavigationBarColor(
                        color = colorNavBarColor,
                        darkIcons = isUseDarkIcons
                    )
                }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(appState.snackbarHostState) { data ->
                            Card(
                                modifier = Modifier.padding(16.dp),
                                shape = ShapeDefaults.Large,
                                elevation = CardDefaults.cardElevation(4.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.onPrimary,
                                    contentColor = MaterialTheme.colorScheme.onSecondary,
                                )
                            ) {
                                Box(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .align(Alignment.CenterHorizontally),
                                ) {
                                    Text(
                                        text = data.visuals.message,
                                        style = MaterialTheme.typography.displaySmall,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    },
                    bottomBar = {
                        if (appBarState.isVisibleBottomBar) {
                            DefaultBottomNavBar(navController)
                        }
                    },
                    topBar = {
                        if (appBarState.isVisibleTopBar) {
                            DefaultTopBar(
                                titleId = appBarState.titleId,
                                navigationIcon = appBarState.navigationIcon,
                                navigationOnClick = appBarState.onClickNavigationBtn,
                                actions = appBarState.actions
                            )
                        }
                    }) { inner ->
                    CompositionLocalProvider(LocalAppState provides appState) {
                        AnimatedNavHost(
                            modifier = Modifier
                                .systemBarsPadding()
                                .navigationBarsPadding(),
                            navController = navController,
                            startDestination = startDestination
                        ) {
                            composable(
                                route = Screen.Home.route,
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() },
                            ) {
                                if (startDestination != Screen.Home.route) {
                                    startDestination = Screen.Home.route
                                }
                                HomeScreen(
                                    onNavigateToMentalTips = navController.navigateToMentalTipsCategories(),
                                    onNavigateToFavorites = navController.navigateToFavorites(),
                                    onNavigateToHelpForYou = navController.navigateToHelpForYou(),
                                    onNavigateToCats = navController.navigateToCats(),
                                    onNavigateToDetail = navController.navigateToDayDetail(),
                                    onNavigateToAddDay = navController.navigateToAddDay(),
                                    viewModel = getHomeViewModel
                                )
                            }

                            composable(
                                route = Screen.Favorites.route,
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() }
                            ) {
                                FavoritesScreen(
                                    onNavigateToDetail = navController.navigateToDayDetail(),
                                )
                            }

                            composable(
                                route = Screen.Cats.route,
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() }
                            ) {
                                CatsScreen()
                            }

                            composable(
                                route = Screen.HelpForYou.route,
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() },
                                popEnterTransition = { materialSlideIn(false) }
                            ) {
                                HelpForYouScreen(
                                    onNavigateToHelplines = navController.navigateToHelplines()
                                )
                            }

                            composable(
                                route = Screen.Helplines.route,
                                enterTransition = { materialSlideIn(true) },
                                exitTransition = { materialSlideOut(true) },
                            ) {
                                HelplinesScreen()
                            }

                            composable(
                                route = Screen.MentalTipsCategories.route,
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() },
                                popEnterTransition = { materialSlideIn(false) }
                            ) {
                                MentalTipsCategoriesScreen(
                                    onNavigateToMentalTips = navController.navigateToMentalTips()
                                )
                            }

                            composable(
                                route = Screen.MentalTips.route + "/{${Constants.CATEGORY_KEY}}",
                                arguments = listOf(navArgument(Constants.CATEGORY_KEY) {
                                    type = NavType.StringType
                                }),
                                enterTransition = { materialSlideIn(true) },
                                exitTransition = { materialSlideOut(true) },
                            ) {
                                MentalTipsScreen()
                            }

                            composable(
                                route = Screen.AddDay.route,
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() }
                            ) {
                                AddDayScreen()
                            }

                            composable(
                                route = Screen.History.route,
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() },
                            ) {
                                HistoryScreen(
                                    onNavigateToDetail = navController.navigateToDayDetail()
                                )
                            }

                            composable(
                                route = Screen.Detail.route + "/{${Constants.DAY_ID_KEY}}",
                                arguments = listOf(navArgument(Constants.DAY_ID_KEY) {
                                    type = NavType.LongType
                                }),
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() }
                            ) {
                                DetailScreen()
                            }

                            composable(
                                route = Screen.Settings.route,
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() }
                            ) {
                                SettingsScreen(
                                    onNavigateSignIn = navController.navigateToSignIn()
                                )
                            }

                            composable(
                                route = Screen.SignInMethods.route,
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() }
                            ) {
                                SignInMethodsScreen(
                                    onNavigateSignInEmail = navController.navigateToSignInEmail()
                                )
                            }

                            composable(
                                route = Screen.SignInWithEmail.route,
                                enterTransition = { materialSlideIn(true) },
                                exitTransition = { materialSlideOut(true) },
                                popEnterTransition = { materialSlideIn(false) }
                            ) {
                                SignInEmailScreen(
                                    onNavigateSettings = navController.popBackStackToSettings(),
                                    onNavigateSignUp = navController.navigateToSignUpEmail(),
                                    onNavigateResetPassword = navController.navigateToResetPassword()
                                )
                            }

                            composable(
                                route = Screen.SignUpWithEmail.route,
                                enterTransition = { materialSlideIn(true) },
                                exitTransition = { materialSlideOut(true) }
                            ) {
                                SignUpEmailScreen(
                                    onNavigateSettings = navController.popBackStackToSettings()
                                )
                            }

                            composable(
                                route = Screen.ResetPassEmail.route,
                                enterTransition = { materialSlideIn(true) },
                                exitTransition = { materialSlideOut(true) }
                            ) {
                                ResetPasswordScreen()
                            }

                            composable(
                                route = Screen.SecureEntry.route,
                                enterTransition = { materialFadeThroughIn() },
                                exitTransition = { materialFadeThroughOut() },
                            ) {
                                SecureEntryScreen(
                                    onNavigateHomeScreen = navController.navigateToHome()
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
