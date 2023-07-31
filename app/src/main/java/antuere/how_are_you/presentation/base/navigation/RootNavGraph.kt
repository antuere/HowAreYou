package antuere.how_are_you.presentation.base.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import antuere.domain.util.Constants
import antuere.how_are_you.presentation.base.ui_animations.materialFadeThroughIn
import antuere.how_are_you.presentation.base.ui_animations.materialFadeThroughOut
import antuere.how_are_you.presentation.base.ui_animations.materialSharedAxisXIn
import antuere.how_are_you.presentation.base.ui_animations.materialSharedAxisXOut
import antuere.how_are_you.presentation.base.ui_animations.materialSharedAxisZIn
import antuere.how_are_you.presentation.base.ui_animations.materialSharedAxisZOut
import antuere.how_are_you.presentation.screens.account_settings.AccountSettingsScreen
import antuere.how_are_you.presentation.screens.add_day.AddDayScreen
import antuere.how_are_you.presentation.screens.cats.CatsScreen
import antuere.how_are_you.presentation.screens.customization.CustomizationScreen
import antuere.how_are_you.presentation.screens.detail.DetailScreen
import antuere.how_are_you.presentation.screens.favorites.FavoritesScreen
import antuere.how_are_you.presentation.screens.help_for_you.HelpForYouScreen
import antuere.how_are_you.presentation.screens.helplines.HelplinesScreen
import antuere.how_are_you.presentation.screens.history.HistoryScreen
import antuere.how_are_you.presentation.screens.home.HomeScreen
import antuere.how_are_you.presentation.screens.home.HomeViewModel
import antuere.how_are_you.presentation.screens.mental_tips.MentalTipsScreen
import antuere.how_are_you.presentation.screens.mental_tips_categories.MentalTipsCategoriesScreen
import antuere.how_are_you.presentation.screens.onboard.OnboardScreen
import antuere.how_are_you.presentation.screens.reset_password.ResetPasswordScreen
import antuere.how_are_you.presentation.screens.secure_entry.SecureEntryScreen
import antuere.how_are_you.presentation.screens.settings.SettingsScreen
import antuere.how_are_you.presentation.screens.sign_in_methods.SignInMethodsScreen
import antuere.how_are_you.presentation.screens.sign_in_with_email.SignInEmailScreen
import antuere.how_are_you.presentation.screens.sign_up_with_email.SignUpEmailScreen
import com.google.accompanist.navigation.animation.composable


@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.initRootNavGraph(
    navController: NavController,
    homeViewModel: () -> HomeViewModel,
) {
    composable(
        route = Screen.Onboard.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        OnboardScreen(onNavigateHomeScreen = navController.navigateToHome())
    }

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
            viewModel = homeViewModel
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
        },
    ) {
        SettingsScreen(
            onNavigateSignIn = navController.navigateToSignIn(),
            onNavigateAccountSettings = navController.navigateToAccountSettings(),
            onNavigateDayCustomization = navController.navigateToDayCustomization()
        )
    }

    composable(
        route = Screen.AccountSettings.route,
        enterTransition = { materialSharedAxisZIn(forward = true) },
        exitTransition = { materialSharedAxisZOut(forward = false) }
    ) {
        AccountSettingsScreen()
    }

    composable(
        route = Screen.Customization.route,
        enterTransition = { materialSharedAxisZIn(forward = true) },
        exitTransition = { materialSharedAxisZOut(forward = false) }
    ) {
        CustomizationScreen()
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
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        SecureEntryScreen(onNavigateHomeScreen = navController.navigateToHome())
    }
}