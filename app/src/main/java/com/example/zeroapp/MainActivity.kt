package com.example.zeroapp

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
import antuere.domain.dto.Settings
import antuere.domain.repository.SettingsRepository
import antuere.domain.util.Constants
import com.example.zeroapp.presentation.add_day.AddDayScreen
import com.example.zeroapp.presentation.base.*
import com.example.zeroapp.presentation.base.ui_animations.materialFadeThroughIn
import com.example.zeroapp.presentation.base.ui_animations.materialFadeThroughOut
import com.example.zeroapp.presentation.base.ui_animations.materialSlideIn
import com.example.zeroapp.presentation.base.ui_animations.materialSlideOut
import com.example.zeroapp.presentation.base.ui_compose_components.AppState
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.navigation.Screen
import com.example.zeroapp.presentation.base.ui_compose_components.bottom_nav_bar.DefaultBottomNavBar
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialogListener
import com.example.zeroapp.presentation.base.ui_compose_components.rememberAppState
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.DefaultTopBar
import com.example.zeroapp.presentation.home.HomeViewModel
import com.example.zeroapp.presentation.base.ui_theme.HowAreYouTheme
import com.example.zeroapp.presentation.cats.CatsScreen
import com.example.zeroapp.presentation.detail.DetailScreen
import com.example.zeroapp.presentation.favorites.FavoritesScreen
import com.example.zeroapp.presentation.help_for_you.HelpForYouScreen
import com.example.zeroapp.presentation.helplines.HelplinesScreen
import com.example.zeroapp.presentation.history.HistoryScreen
import com.example.zeroapp.presentation.home.HomeScreen
import com.example.zeroapp.presentation.mental_tips.MentalTipsScreen
import com.example.zeroapp.presentation.mental_tips_categories.MentalTipsCategoriesScreen
import com.example.zeroapp.presentation.reset_password.ResetPasswordScreen
import com.example.zeroapp.presentation.secure_entry.SecureEntryScreen
import com.example.zeroapp.presentation.sign_in_with_email.SignInEmailScreen
import com.example.zeroapp.presentation.settings.SettingsScreen
import com.example.zeroapp.presentation.sign_in_methods.SignInMethodsScreen
import com.example.zeroapp.presentation.sign_up_with_email.SignUpEmailScreen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject


@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var settingsRepository: SettingsRepository

    @Inject
    lateinit var signInClient: GoogleSignInClient

    private var settings: Settings? = null

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        WindowCompat.setDecorFitsSystemWindows(window, false)
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"))

        val job = lifecycleScope.launch(Dispatchers.IO) {
            settings = settingsRepository.getSettings().first()
        }

        installSplashScreen().apply {
            if (BuildConfig.BUILD_TYPE != "benchmark") {
                setKeepOnScreenCondition {
                    viewModel.isShowSplash.value
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
                val appState: AppState by rememberAppState()
                val appBarState by appState.appBarState
                val navController = appState.navController

                val uiDialogListener by remember {
                    mutableStateOf(UIDialogListener())
                }
                uiDialogListener.SetupDialogListener()

                val systemUiController = rememberSystemUiController()
                val isUseDarkIcons =
                    !(isSystemInDarkTheme() || (!isSystemInDarkTheme() && appBarState.isVisibleBottomBar))
                val colorNavBarColor =
                    if (appBarState.isVisibleBottomBar) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary

                SideEffect {
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
                                navigationOnClick = appBarState.navigationOnClick,
                                actions = appBarState.actions
                            )
                        }
                    }) { innerPadding ->
                    AnimatedNavHost(
                        modifier = Modifier.padding(innerPadding),
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
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                showSnackbar = { message: String ->
                                    appState.showSnackbar(message)
                                },
                                dismissSnackbar = {
                                    appState.dismissSnackbar()
                                },
                                onNavigateToMentalTips = { navController.navigate(Screen.MentalTipsCategories.route) },
                                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) },
                                onNavigateToHelpForYou = { navController.navigate(Screen.HelpForYou.route) },
                                onNavigateToCats = { navController.navigate(Screen.Cats.route) },
                                onNavigateToDetail = { navController.navigate(Screen.Detail.route + "/$it") },
                                onNavigateToAddDay = { navController.navigate(Screen.AddDay.route) }
                            )
                        }

                        composable(
                            route = Screen.Favorites.route,
                            enterTransition = { materialFadeThroughIn() },
                            exitTransition = { materialFadeThroughOut() }
                        ) {
                            FavoritesScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                onNavigateToDetail = {
                                    navController.navigate(Screen.Detail.route + "/$it")
                                },
                                onNavigateUp = { navController.navigateUp() },
                                showDialog = { uiDialogListener.showDialog(it) }
                            )
                        }

                        composable(
                            route = Screen.Cats.route,
                            enterTransition = { materialFadeThroughIn() },
                            exitTransition = { materialFadeThroughOut() }
                        ) {
                            CatsScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                onNavigateUp = { navController.navigateUp() },
                            )
                        }

                        composable(
                            route = Screen.HelpForYou.route,
                            enterTransition = { materialFadeThroughIn() },
                            exitTransition = { materialFadeThroughOut() },
                            popEnterTransition = { materialSlideIn(false) }
                        ) {
                            HelpForYouScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                onNavigateUp = { navController.navigateUp() },
                                onNavigateToHelplines = { navController.navigate(Screen.Helplines.route) }
                            )
                        }

                        composable(
                            route = Screen.Helplines.route,
                            enterTransition = { materialSlideIn(true) },
                            exitTransition = { materialSlideOut(true) },
                        ) {
                            HelplinesScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                onNavigateUp = { navController.navigateUp() },
                            )
                        }

                        composable(
                            route = Screen.MentalTipsCategories.route,
                            enterTransition = { materialFadeThroughIn() },
                            exitTransition = { materialFadeThroughOut() },
                            popEnterTransition = { materialSlideIn(false) }
                        ) {
                            MentalTipsCategoriesScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                onNavigateUp = { navController.navigateUp() },
                                onNavigateToMentalTip = { navController.navigate(Screen.MentalTips.route + "/$it") }
                            )
                        }

                        composable(
                            route = Screen.MentalTips.route + "/{${Constants.CATEGORY_KEY}}",
                            enterTransition = { materialSlideIn(true) },
                            exitTransition = { materialSlideOut(true) },
                        ) {
                            MentalTipsScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                onNavigateUp = { navController.navigateUp() },
                            )
                        }

                        composable(
                            route = Screen.AddDay.route,
                            enterTransition = { materialFadeThroughIn() },
                            exitTransition = { materialFadeThroughOut() }
                        ) {
                            AddDayScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                onNavigateUp = { navController.navigateUp() },
                            )
                        }

                        composable(
                            route = Screen.History.route,
                            enterTransition = { materialFadeThroughIn() },
                            exitTransition = { materialFadeThroughOut() },
                        ) {
                            HistoryScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                dismissSnackbar = { appState.dismissSnackbar() },
                                onNavigateToDetail = {
                                    navController.navigate(Screen.Detail.route + "/$it")
                                },
                                showDialog = { uiDialogListener.showDialog(it) }
                            )
                        }

                        composable(
                            route = Screen.Detail.route + "/{${Constants.DAY_ID_KEY}}",
                            enterTransition = { materialFadeThroughIn() },
                            exitTransition = { materialFadeThroughOut() }
                        ) {
                            DetailScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                onNavigateUp = { navController.navigateUp() })
                        }

                        composable(
                            route = Screen.Settings.route,
                            enterTransition = { materialFadeThroughIn() },
                            exitTransition = { materialFadeThroughOut() }
                        ) {
                            SettingsScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                showSnackbar = { message: String ->
                                    appState.showSnackbar(message)
                                },
                                onNavigateSignIn = { navController.navigate(Screen.SignInMethods.route) },
                            )
                        }

                        composable(
                            route = Screen.SignInMethods.route,
                            enterTransition = { materialFadeThroughIn() },
                            exitTransition = { materialFadeThroughOut() }
                        ) {
                            SignInMethodsScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                showSnackbar = { message: String ->
                                    appState.showSnackbar(message)
                                },
                                onNavigateUp = { navController.navigateUp() },
                                onNavigateSignInEmail = { navController.navigate(Screen.SignInWithEmail.route) },
                                signInClient = signInClient,
                            )
                        }

                        composable(
                            route = Screen.SignInWithEmail.route,
                            enterTransition = { materialSlideIn(true) },
                            exitTransition = { materialSlideOut(true) },
                            popEnterTransition = { materialSlideIn(false) }
                        ) {
                            SignInEmailScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                showSnackbar = { message: String ->
                                    appState.showSnackbar(message)
                                },
                                onNavigateUp = { navController.navigateUp() },
                                onNavigateSettings = {
                                    navController.popBackStack(
                                        Screen.Settings.route,
                                        false
                                    )
                                },
                                onNavigateSignUp = { navController.navigate(Screen.SignUpWithEmail.route) },
                                onNavigateResetPassword = { navController.navigate(Screen.ResetPassEmail.route) },
                            )
                        }

                        composable(
                            route = Screen.SignUpWithEmail.route,
                            enterTransition = { materialSlideIn(true) },
                            exitTransition = { materialSlideOut(true) }
                        ) {
                            SignUpEmailScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                showSnackbar = { message: String ->
                                    appState.showSnackbar(message)
                                },
                                onNavigateSettings = {
                                    navController.popBackStack(
                                        Screen.Settings.route,
                                        false
                                    )
                                },
                                onNavigateUp = { navController.navigateUp() },
                            )
                        }

                        composable(
                            route = Screen.ResetPassEmail.route,
                            enterTransition = { materialSlideIn(true) },
                            exitTransition = { materialSlideOut(true) }
                        ) {
                            ResetPasswordScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                showSnackbar = { message: String ->
                                    appState.showSnackbar(message)
                                },
                                onNavigateUp = { navController.navigateUp() },
                            )
                        }

                        composable(
                            route = Screen.SecureEntry.route,
                            enterTransition = { materialFadeThroughIn() },
                            exitTransition = { materialFadeThroughOut() },
                        ) {
                            SecureEntryScreen(
                                updateAppBar = { barState: AppBarState ->
                                    appState.appBarState.value = barState
                                },
                                showSnackbar = { message: String ->
                                    appState.showSnackbar(message)
                                },
                                onNavigateHomeScreen = { navController.navigate(Screen.Home.route) },
                            )
                        }
                    }
                }
            }
        }
    }
}
