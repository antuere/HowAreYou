package antuere.how_are_you.presentation.home

import antuere.domain.dto.Day
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText

object MyAnalystForHome {

    const val DEFAULT_WISH = -1

    fun getWishStringForSummary(id: Int): UiText {
        return when (id) {
            antuere.data.R.drawable.smile_very_happy -> UiText.StringResource(R.string.wish_for_users_very_happy)
            antuere.data.R.drawable.smile_happy -> UiText.StringResource(R.string.wish_for_users_happy)
            antuere.data.R.drawable.smile_low -> UiText.StringResource(R.string.wish_for_users_low)
            antuere.data.R.drawable.smile_none -> UiText.StringResource(R.string.wish_for_users_none)
            antuere.data.R.drawable.smile_sad -> UiText.StringResource(R.string.wish_for_users_sad)
            else -> UiText.StringResource(R.string.wish_for_users_plug)
        }
    }

    fun isShowWarningForSummary(days: List<Day>): Boolean {
        if (days.isEmpty()) return false

        var result = true

        days.forEach { day ->
            if (day.imageResId != antuere.data.R.drawable.smile_none && day.imageResId != antuere.data.R.drawable.smile_sad) {
                result = false
            }
        }

        return result
    }
}