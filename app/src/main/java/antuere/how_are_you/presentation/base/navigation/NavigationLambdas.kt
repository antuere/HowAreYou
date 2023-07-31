package antuere.how_are_you.presentation.base.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController


fun NavController.navigateToSecure() {
    this.navigate(Screen.SecureEntry.route) {
        popUpTo(this@navigateToSecure.graph.id)
        launchSingleTop = true
    }
}

@Composable
fun NavController.navigateToHome(): () -> Unit = remember {
    {
        this.popBackStack()
        this.navigate(Screen.Home.route)
    }
}

@Composable
fun NavController.navigateToDayDetail(): (Long) -> Unit = remember {
    { this.navigate(Screen.Detail.route + "/$it") }
}

@Composable
fun NavController.navigateToMentalTips(): (String) -> Unit = remember {
    { this.navigate(Screen.MentalTips.route + "/$it") }
}

@Composable
fun NavController.navigateToSignIn(): () -> Unit = remember {
    { this.navigate(Screen.SignInMethods.route) }
}

@Composable
fun NavController.navigateToSignInEmail(): () -> Unit = remember {
    { this.navigate(Screen.SignInWithEmail.route) }
}

@Composable
fun NavController.navigateToSignUpEmail(): () -> Unit = remember {
    { this.navigate(Screen.SignUpWithEmail.route) }
}

@Composable
fun NavController.navigateToResetPassword(): () -> Unit = remember {
    { this.navigate(Screen.ResetPassEmail.route) }
}

@Composable
fun NavController.popBackStackToSettings(): () -> Unit = remember {
    { this.popBackStack(Screen.Settings.route, false) }
}


@Composable
fun NavController.navigateToHelplines(): () -> Unit = remember {
    { this.navigate(Screen.Helplines.route) }
}

@Composable
fun NavController.navigateToMentalTipsCategories(): () -> Unit = remember {
    { this.navigate(Screen.MentalTipsCategories.route) }
}

@Composable
fun NavController.navigateToFavorites(): () -> Unit = remember {
    { this.navigate(Screen.Favorites.route) }
}

@Composable
fun NavController.navigateToAccountSettings(): () -> Unit = remember {
    { this.navigate(Screen.AccountSettings.route) }
}

@Composable
fun NavController.navigateToDayCustomization(): () -> Unit = remember {
    { this.navigate(Screen.Customization.route) }
}

@Composable
fun NavController.navigateToHelpForYou(): () -> Unit = remember {
    { this.navigate(Screen.HelpForYou.route) }
}

@Composable
fun NavController.navigateToCats(): () -> Unit = remember {
    { this.navigate(Screen.Cats.route) }
}

@Composable
fun NavController.navigateToAddDay(): () -> Unit = remember {
    { this.navigate(Screen.AddDay.route) }
}



