package antuere.how_are_you.presentation.home.state

import androidx.annotation.DrawableRes
import antuere.how_are_you.R

sealed class FabButtonState(
    @DrawableRes val image: Int
) {
    data class Smile(val imageId: Int, val dayId: Long) : FabButtonState(imageId)
    object Add : FabButtonState(R.drawable.ic_plus)
}