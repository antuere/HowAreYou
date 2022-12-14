package com.example.zeroapp.presentation.base.ui_dialog

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class UIDialog(
    @StringRes val title: Int,
    @StringRes val desc: Int,
    @DrawableRes val icon: Int,
    val positiveButton: UiButton,
    val negativeButton: UiButton,
    val neutralButton : UiButton? = null
) {
    data class UiButton(
        @StringRes val text: Int,
        val onClick: () -> Unit
    )
}
