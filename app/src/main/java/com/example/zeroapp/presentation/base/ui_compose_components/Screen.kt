package com.example.zeroapp.presentation.base.ui_compose_components

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object AddDay : Screen("add_day_screen")
    object Favorites : Screen("favorites_screen")
    object Cats : Screen("cats_screen")
    object MentalTipsCategories : Screen("mental_tips_categories_screen")
    object MentalTips : Screen("mental_tips_screen")
    object History : Screen("history_screen")
    object Detail : Screen("detail_screen")
    object Settings : Screen("settings_screen")
    object SignInMethods : Screen("sign_in_methods_screen")
    object SignInWithEmail : Screen("sign_in_with_email_screen")
    object SignUpWithEmail : Screen("sign_up_with_email_screen")
    object ResetPassEmail : Screen("reset_pass_email_screen")
    object SecureEntry : Screen("secure_entry_screen")
}
