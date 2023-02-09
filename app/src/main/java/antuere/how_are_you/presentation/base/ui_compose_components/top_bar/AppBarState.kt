package antuere.how_are_you.presentation.base.ui_compose_components.top_bar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import antuere.how_are_you.R

data class AppBarState(
    @StringRes val titleId: Int = R.string.home,
    val navigationIcon: ImageVector? = null,
    val onClickNavigationBtn: () -> Unit = {},
    val actions: (@Composable RowScope.() -> Unit) = {},
    val isVisibleTopBar : Boolean = true,
    val isVisibleBottomBar : Boolean = true
)
