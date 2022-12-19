package com.example.zeroapp

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.ui.setupWithNavController
import antuere.domain.dto.Settings
import antuere.domain.usecases.user_settings.GetSettingsUseCase
import antuere.domain.util.Constants
import com.example.zeroapp.databinding.ActivityMainBinding
import com.example.zeroapp.presentation.add_day.AddDayScreen
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.Screen
import com.example.zeroapp.presentation.base.ui_compose_components.BottomNavBar
import com.example.zeroapp.presentation.base.ui_compose_components.DefaultTopAppBar
import com.example.zeroapp.presentation.home.HomeViewModel
import com.example.zeroapp.presentation.base.ui_theme.HowAreYouTheme
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
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    private var _bottomNavView: BottomNavigationView? = null
    val bottomNavView: BottomNavigationView?
        get() = _bottomNavView

    var toolbar: MaterialToolbar? = null
    private var navController: NavController? = null

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
                                snackbarHostState = snackbarHostState)
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
                                snackbarHostState = snackbarHostState)
                        }

                        composable(route = Screen.ResetPassEmail.route) {
                            ResetPasswordScreen(
                                onComposing = { barState: AppBarState, isShow: Boolean ->
                                    appBarState = barState
                                    isShowBottomBar = isShow
                                },
                                onNavigateUp = { navController.navigateUp() },
                                snackbarHostState = snackbarHostState)
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

    @Composable
    private fun RowUi(modifier: Modifier = Modifier, string: String) {

        val expended = rememberSaveable {
            mutableStateOf(false)
        }

        val extraPadding by animateDpAsState(
            if (expended.value) 45.dp else 0.dp,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow,
            )
        )
        Surface(
            modifier = modifier,
            color = MaterialTheme.colorScheme.primary,
        ) {
            Column {
                Row(
                    modifier = Modifier.padding(15.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = string,
                        modifier = Modifier
                            .weight(1F)
                            .padding(bottom = extraPadding.coerceAtLeast(0.dp)),
                        textAlign = TextAlign.Start,
                        fontSize = 20.sp
                    )

                    IconButton(
                        onClick = { expended.value = expended.value.not() }) {

                        Icon(
                            imageVector = if (expended.value) Icons.Filled.ExpandLess else Icons.Filled.ExpandMore,
                            contentDescription = if (expended.value) stringResource(R.string.click_less) else stringResource(
                                R.string.click_more
                            )
                        )
                    }
                }

                if (expended.value) {
                    Text(
                        text = "just new added text, need test, fucking plug!!",
                        textAlign = TextAlign.Start,
                        fontSize = 32.sp
                    )
                }
            }
        }
    }


    @Composable
    fun InitUi(modifier: Modifier = Modifier) {

        var shouldShowOnboarding by rememberSaveable { mutableStateOf(true) }

        if (shouldShowOnboarding) {
            OnboardingScreen(onClicked = { shouldShowOnboarding = false })
        } else {
            ColumnList()
        }
    }

    @Composable
    fun OnboardingScreen(modifier: Modifier = Modifier, onClicked: () -> Unit = {}) {
        Column(
            modifier = modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onClicked
            ) {
                Text("Continue")
            }
        }
    }

    @Composable
    private fun ColumnList(list: List<String> = List(500) { "$it element" }) {
        val context = LocalContext.current
        remember(context) {
            (context as FragmentActivity).supportFragmentManager
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(5.dp),
            contentPadding = PaddingValues(vertical = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items(list) {
                RowUi(
                    string = it, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp)
                )
            }
        }
    }

//    @Composable
//    private fun MyTopAppBar() {
//       TopAppBar() {
//
//        }
//    }


//    @Preview(showBackground = true)
//    @Composable
//    fun OnboardingPreview() {
//        HowAreYouTheme() {
//            Scaffold(bottomBar = { BottomNavBar(nav) }) { padding ->
//                InitUi(modifier = Modifier.padding(padding))
//            }
//        }
//    }

    private fun setupBinding(binding: ActivityMainBinding) {
        Timber.plant(Timber.DebugTree())

        toolbar = binding.toolBarApp
//        setSupportActionBar(toolbar)
        _bottomNavView = binding.bottomView

//        val navHostFragment =
//            supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment

//        navController = navHostFragment.navController
//        val appBarConfiguration = AppBarConfiguration(
//            setOf(
//                R.id.settingsFragment,
//                R.id.historyFragment,
//                R.id.summaryFragment,
//            )
//        )

        val navInflater = navController!!.navInflater
        val graph = navInflater.inflate(R.navigation.navigation)

        if (!settings!!.isPinCodeEnabled && !settings!!.isBiometricEnabled) {
            graph.setStartDestination(R.id.summaryFragment)
        } else {
            graph.setStartDestination(R.id.secureEntryFragment)
        }

        navController?.graph = graph

        navController?.addOnDestinationChangedListener { _, dest, _ ->
            when (dest.id) {
                R.id.settingsFragment -> {
                    showBottomBar()
                }
                R.id.historyFragment -> {
                    showBottomBar()
                }
                R.id.addDayFragment -> hideBottomBar()
                R.id.summaryFragment -> {
                    showUiElements()
                    navController!!.graph.setStartDestination(dest.id)
                }
                R.id.detailFragment -> hideBottomBar()
                R.id.favoritesFragment -> hideBottomBar()
                R.id.catsFragment -> hideBottomBar()
                R.id.loginFragment -> hideBottomBar()
                R.id.registerFragment -> hideBottomBar()
                R.id.resetPasswordFragment -> hideBottomBar()
                R.id.signInMethodsFragment -> hideBottomBar()
                R.id.mentalTipsFragment -> hideBottomBar()
                R.id.secureEntryFragment -> {
                    goneBottomBar()
                    goneAppBar()
                }
            }
        }

//        setupActionBarWithNavController(navController!!, appBarConfiguration)
        _bottomNavView?.setupWithNavController(navController!!)
    }

//    override fun onSupportNavigateUp(): Boolean {
//        return navController?.navigateUp() ?: false
//    }

    private fun hideBottomBar() {
        _bottomNavView?.visibility = View.INVISIBLE
    }

    private fun goneBottomBar() {
        _bottomNavView?.visibility = View.GONE
    }

    private fun showBottomBar() {
        _bottomNavView?.visibility = View.VISIBLE
    }

    private fun goneAppBar() {
        toolbar!!.visibility = View.GONE
    }

    private fun showUiElements() {
        lifecycleScope.launch {
            delay(100)
            toolbar!!.visibility = View.VISIBLE
            _bottomNavView?.visibility = View.VISIBLE
        }

    }
}