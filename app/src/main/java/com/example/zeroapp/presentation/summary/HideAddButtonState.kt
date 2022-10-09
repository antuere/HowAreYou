package com.example.zeroapp.presentation.summary

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.zeroapp.R

sealed class HideAddButtonState(
    @StringRes val tag: Int,
    @DrawableRes val image: Int,
    @StringRes val transitionName: Int
) {
    class Smile(imageId:Int):HideAddButtonState(
        R.string.smile,
        imageId,
        R.string.transition_name
    )
    object Add:HideAddButtonState(
        R.string.add,
        R.drawable.ic_plus,
        R.string.transition_name_for_sum
    )

}