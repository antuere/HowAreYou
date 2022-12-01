package com.example.zeroapp.presentation.base.ui_compose_components

sealed class Screen(val route : String){
    object HomeScreen : Screen("home_screen")
    object HistoryScreen : Screen("history_screen")
    object Settings : Screen("settings_screen")
    object SignInMethods : Screen("sign_in_methods_screen")
}
