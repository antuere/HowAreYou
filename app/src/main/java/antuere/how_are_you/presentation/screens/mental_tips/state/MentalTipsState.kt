package antuere.how_are_you.presentation.screens.mental_tips.state

import androidx.annotation.StringRes
import antuere.domain.dto.mental_tips.MentalTip
import antuere.how_are_you.R

sealed class MentalTipsState(@StringRes val appBarTitleId: Int) {
    data class Loading(val titleId: Int = R.string.mental_tips) : MentalTipsState(titleId)
    data class Loaded(@StringRes val titleId: Int, val listMentalTips: List<MentalTip>) :
        MentalTipsState(titleId)
}