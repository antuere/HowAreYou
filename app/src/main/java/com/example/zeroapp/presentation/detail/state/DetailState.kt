package com.example.zeroapp.presentation.detail.state

import androidx.annotation.DrawableRes
import com.example.zeroapp.R

data class DetailState(
    val isLoading: Boolean = true,
    @DrawableRes val daySmileRes: Int = antuere.data.R.drawable.smile_happy,
    @DrawableRes val favoriteBtnRes: Int = R.drawable.ic_baseline_favorite_border,
    val dayText: String = "",
    val dateString: String = "",
    val isFavorite: Boolean = false
)
