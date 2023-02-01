package antuere.how_are_you.presentation.base.ui_compose_components.top_bar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import antuere.how_are_you.R

data class AppBarState(
    @StringRes var titleId: Int = R.string.home,
    var navigationIcon: ImageVector? = null,
    var onClickNavigationBtn: () -> Unit = {},
    var actions: (@Composable RowScope.() -> Unit) = {},
    var isVisibleTopBar : Boolean = true,
    var isVisibleBottomBar : Boolean = true
)
