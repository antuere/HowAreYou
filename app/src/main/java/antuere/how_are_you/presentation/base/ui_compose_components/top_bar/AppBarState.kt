package antuere.how_are_you.presentation.base.ui_compose_components.top_bar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import antuere.how_are_you.presentation.base.ui_text.UiText

data class AppBarState(
    val topBarTitle: UiText? = null,
    val topBarType: TopBarType = TopBarType.CENTER_ALIGNED,
    val navigationIcon: ImageVector? = null,
    val onClickNavigationBtn: () -> Unit = {},
    val actions: (@Composable RowScope.() -> Unit) = {},
    val isVisibleTopBar: Boolean = true,
    val isVisibleBottomBar: Boolean = true,
)

enum class TopBarType {
    CENTER_ALIGNED,
    SMALL,
    MEDIUM,
    LARGE
}
