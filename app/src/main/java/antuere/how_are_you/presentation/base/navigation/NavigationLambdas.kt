package antuere.how_are_you.presentation.base.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController

@Composable
fun NavController.navigateToDayDetail(): (Long) -> Unit = remember {
    { this.navigate(Screen.Detail.route + "/$it") }
}

@Composable
fun NavController.navigateToSignIn(): () -> Unit = remember {
    { this.navigate(Screen.SignInMethods.route) }
}

@Composable
fun NavController.navigateToHelplines(): () -> Unit = remember {
    { this.navigate(Screen.Helplines.route) }
}

@Composable
fun NavController.navigateToMentalTips(): () -> Unit = remember {
    { this.navigate(Screen.MentalTipsCategories.route) }
}

@Composable
fun NavController.navigateToFavorites(): () -> Unit = remember {
    { this.navigate(Screen.Favorites.route) }
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



