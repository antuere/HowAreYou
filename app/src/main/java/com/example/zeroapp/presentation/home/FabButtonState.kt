package com.example.zeroapp.presentation.home

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.zeroapp.R

sealed class FabButtonState(
    @StringRes val tag: Int,
    @DrawableRes val image: Int,
    @StringRes val transitionName: Int
) {
    class Smile(imageId: Int) : FabButtonState(
        R.string.smile,
        imageId,
        R.string.transition_name
    )

    object Add : FabButtonState(
        R.string.add,
        R.drawable.ic_plus,
        R.string.transition_name_for_sum
    )

}