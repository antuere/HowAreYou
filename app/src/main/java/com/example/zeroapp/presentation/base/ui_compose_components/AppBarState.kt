package com.example.zeroapp.presentation.base.ui_compose_components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import com.example.zeroapp.R

data class AppBarState(
    @StringRes val titleId: Int = R.string.home,
    val navigationIcon: ImageVector? = null,
    val navigationOnClick: () -> Unit = {},
    val actions: (@Composable RowScope.() -> Unit) = {},
    var isVisible : Boolean = true
)
