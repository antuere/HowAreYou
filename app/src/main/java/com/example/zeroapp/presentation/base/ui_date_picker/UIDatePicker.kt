package com.example.zeroapp.presentation.base.ui_date_picker

import androidx.annotation.StringRes
import androidx.core.util.Pair

data class UIDatePicker(
    @StringRes val title: Int,
    val positiveButton: UiButtonPositive,
    val negativeButton: UiButtonNegative
) {
    data class UiButtonPositive(
        val onClick: (Pair<Long, Long>) -> Unit
    )

    data class UiButtonNegative(
        val onClick: () -> Unit
    )
}
