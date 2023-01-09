package com.example.zeroapp.presentation.base.ui_compose_components.top_bar

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.zeroapp.R

data class AppBarState(
    @StringRes var titleId: Int = R.string.home,
    var navigationIcon: ImageVector? = null,
    var navigationOnClick: () -> Unit = {},
    var actions: (@Composable RowScope.() -> Unit) = {},
    var isVisibleTopBar : Boolean = true,
    var isVisibleBottomBar : Boolean = true
)
