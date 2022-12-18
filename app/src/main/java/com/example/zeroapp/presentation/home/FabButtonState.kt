package com.example.zeroapp.presentation.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.zeroapp.R

sealed class FabButtonState(
    @DrawableRes val image: Int,
    @StringRes val transitionName: Int
) {
    data class Smile(val imageId: Int, val dayId: Long) : FabButtonState(
        imageId,
        R.string.transition_name
    )

    object Add : FabButtonState(
        R.drawable.ic_plus,
        R.string.transition_name_for_sum
    )

}