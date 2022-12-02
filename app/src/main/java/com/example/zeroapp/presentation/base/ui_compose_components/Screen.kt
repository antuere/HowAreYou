package com.example.zeroapp.presentation.base.ui_compose_components

sealed class Screen(val route: String) {
    object HomeScreen : Screen("home_screen")
    object HistoryScreen : Screen("history_screen")
    object Settings : Screen("settings_screen")
    object SignInMethods : Screen("sign_in_methods_screen")
    object SignInWithEmail : Screen("sign_in_with_email")
    object SignUpWithEmail : Screen("sign_up_with_email")
    object ResetPassEmail : Screen("reset_pass_email")
}
