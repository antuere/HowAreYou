package antuere.how_are_you.presentation.base.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText

sealed class BottomDestinations(
    val screen: Screen,
    val iconOutline: ImageVector,
    val iconFilled: ImageVector,
    val title: UiText,
) {
    companion object {
        fun getDestinations(): List<BottomDestinations> {
            return listOf(Home, History, Settings)
        }
    }

    object Home : BottomDestinations(
        screen = Screen.Home,
        iconOutline = Icons.Outlined.Home,
        iconFilled = Icons.Filled.Home,
        title = UiText.StringResource(R.string.home)
    )

    object History : BottomDestinations(
        screen = Screen.History,
        iconOutline = Icons.Outlined.History,
        iconFilled = Icons.Filled.History,
        title = UiText.StringResource(R.string.history)
    )

    object Settings : BottomDestinations(
        screen = Screen.Settings,
        iconOutline = Icons.Outlined.Settings,
        iconFilled = Icons.Filled.Settings,
        title = UiText.StringResource(R.string.settings)
    )
}
