package antuere.how_are_you.presentation.screens.detail.state

import androidx.annotation.DrawableRes
import antuere.how_are_you.R

data class DetailState(
    val isLoading: Boolean = true,
    val isEditMode: Boolean = false,
    @DrawableRes val daySmileRes: Int = antuere.data.R.drawable.smile_happy,
    @DrawableRes val daySmileResEditable: Int = antuere.data.R.drawable.smile_happy,
    @DrawableRes val favoriteBtnRes: Int = R.drawable.ic_baseline_favorite_border,
    val dayText: String = "",
    val dayTextEditable: String = "",
    val dateString: String = "",
    val smileImages: List<Int> = listOf(
        antuere.data.R.drawable.smile_sad,
        antuere.data.R.drawable.smile_none,
        antuere.data.R.drawable.smile_low,
        antuere.data.R.drawable.smile_happy,
        antuere.data.R.drawable.smile_very_happy
    ),
)
