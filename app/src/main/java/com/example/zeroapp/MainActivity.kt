package com.example.zeroapp

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import antuere.domain.dto.Settings
import antuere.domain.usecases.user_settings.GetSettingsUseCase
import antuere.domain.util.Constants
import com.example.zeroapp.presentation.add_day.AddDayScreen
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.Screen
import com.example.zeroapp.presentation.base.ui_compose_components.BottomNavBar
import com.example.zeroapp.presentation.base.ui_compose_components.DefaultTopAppBar
import com.example.zeroapp.presentation.home.HomeViewModel
import com.example.zeroapp.presentation.base.ui_theme.HowAreYouTheme
import com.example.zeroapp.presentation.cats.CatsScreen
import com.example.zeroapp.presentation.detail.DetailScreen
import com.example.zeroapp.presentation.favorites.FavoritesScreen
import com.example.zeroapp.presentation.history.HistoryScreen
import com.example.zeroapp.presentation.history.MyAnalystForHistory
import com.example.zeroapp.presentation.home.HomeScreen
import com.example.zeroapp.presentation.reset_password.ResetPasswordScreen
import com.example.zeroapp.presentation.secure_entry.SecureEntryScreen
import com.example.zeroapp.presentation.sign_in_with_email.SignInEmailScreen
import com.example.zeroapp.presentation.settings.SettingsScreen
import com.example.zeroapp.presentation.sign_in_methods.SignInMethodsScreen
import com.example.zeroapp.presentation.sign_up_with_email.SignUpEmailScreen
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    @Inject
    lateinit var getSettingsUseCase: GetSettingsUseCase

    @Inject
    lateinit var myAnalystForHistory: MyAnalystForHistory

    @Inject
    lateinit var signInClient: GoogleSignInClient

    private var settings: Settings? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())

        lifecycleScope.launch(Dispatchers.IO) {
            settings = getSettingsUseCase(Unit).first()
        }

        installSplashScreen().apply {
            if (BuildConfig.BUILD_TYPE != "benchmark") {
                setKeepOnScreenCondition {
                    viewModel.isShowSplash.value
                }
            }
        }

        setContent {
            HowAreYouTheme {
                var startDestination = Screen.SecureEntry.route

                if (!settings!!.isPinCodeEnabled && !settings!!.isBiometricEnabled) {
                    startDestination = Screen.Home.route
                }

                val navController = rememberNavController()
                val snackbarHostState = remember { SnackbarHostState() }
                var appBarState by remember {
                    mutableStateOf(AppBarState())
                }
                var isShowBottomBar by remember {
                    mutableStateOf(true)
                }

                Scaffold(
                    snackbarHost = {
                        SnackbarHost(snackbarHostState) { data ->
                            Snackbar(
                                modifier = Modifier.padding(16.dp),
                                containerColor = MaterialTheme.colorScheme.onPrimary,
                                contentColor = MaterialTheme.colorScheme.onSecondary,
                                shape = SnackbarDefaults.shape,
                            ) {
                                Box {
                                    Text(
                                        text = data.visuals.message,
                                        style = MaterialTheme.typography.displaySmall
                                    )
                                }
                            }
                        }
                    },
                    bottomBar = {
                        if (isShowBottomBar) {
                            BottomNavBar(navController)
                        }
                    },
                    topBar = {
                        if (appBarState.isVisible) {
                            DefaultTopAppBar(
                                titleId = appBarState.titleId,
                                navigationIcon = appBarState.navigationIcon,
                                navigationOnClick = appBarState.navigationOnClick,
                                actions = appBarState.actions
                            )
                        }
                    }) { innerPadding ->
                    NavHost(
                        modifier = Modifier.padding(innerPadding),
                        navController = navController,
                        startDestination = startDestination
                    ) {

                        composable(route = Screen.Home.route) {
                            startDestination = Screen.Home.route
                            HomeScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                onNavigateToDetail = {
                                    navController.navigate(Screen.Detail.route + "/$it")
                                },
                                onNavigateToAddDay = { navController.navigate(Screen.AddDay.route) },
                                onNavigateToCats = { navController.navigate(Screen.Cats.route) },
                                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) },
                                snackbarHostState = snackbarHostState
                            )
                        }

                        composable(route = Screen.Favorites.route) {
                            FavoritesScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                onNavigateToDetail = {
                                    navController.navigate(Screen.Detail.route + "/$it")
                                },
                                onNavigateUp = { navController.navigateUp() },
                            )
                        }

                        composable(route = Screen.Cats.route) {
                            CatsScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                onNavigateUp = { navController.navigateUp() },
                            )
                        }

                        composable(route = Screen.AddDay.route) {
                            AddDayScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                onNavigateUp = { navController.navigateUp() },
                            )
                        }

                        composable(route = Screen.History.route) {
                            HistoryScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                myAnalystForHistory = myAnalystForHistory,
                                onNavigateToDetail = {
                                    navController.navigate(Screen.Detail.route + "/$it")
                                })
                        }

                        composable(route = Screen.Detail.route + "/{${Constants.DAY_ID_KEY}}") {
                            DetailScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                onNavigateUp = { navController.navigateUp() })
                        }

                        composable(route = Screen.Settings.route) {
                            SettingsScreen(
                                onNavigateSignIn = { navController.navigate(Screen.SignInMethods.route) },
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                snackbarHostState = snackbarHostState
                            )
                        }

                        composable(route = Screen.SignInMethods.route) {
                            SignInMethodsScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                onNavigateUp = { navController.navigateUp() },
                                onNavigateSignInEmail = { navController.navigate(Screen.SignInWithEmail.route) },
                                signInClient = signInClient,
                                snackbarHostState = snackbarHostState
                            )
                        }

                        composable(route = Screen.SignInWithEmail.route) {
                            SignInEmailScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
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
                                snackbarHostState = snackbarHostState
                            )
                        }

                        composable(route = Screen.SignUpWithEmail.route) {
                            SignUpEmailScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                onNavigateSettings = {
                                    navController.popBackStack(
                                        Screen.Settings.route,
                                        false
                                    )
                                },
                                onNavigateUp = { navController.navigateUp() },
                                snackbarHostState = snackbarHostState
                            )
                        }

                        composable(route = Screen.ResetPassEmail.route) {
                            ResetPasswordScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                onNavigateUp = { navController.navigateUp() },
                                snackbarHostState = snackbarHostState
                            )
                        }

                        composable(route = Screen.SecureEntry.route) {
                            SecureEntryScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                onNavigateHomeScreen = { navController.navigate(Screen.Home.route) },
                                snackbarHostState = snackbarHostState
                            )
                        }
                    }
                }
            }
        }
    }
}