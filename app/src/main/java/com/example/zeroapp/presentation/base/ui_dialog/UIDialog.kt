package com.example.zeroapp.presentation.base.ui_dialog

import androidx.annotation.StringRes

data class UIDialog(
    @StringRes val title: Int,
    @StringRes val desc: Int,
    val positiveButton: UiButton,
    val negativeButton: UiButton
) {
    data class UiButton(
        @StringRes val text: Int,
        val onClick: () -> Unit
    )
}