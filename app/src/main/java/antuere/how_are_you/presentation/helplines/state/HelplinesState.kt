package antuere.how_are_you.presentation.helplines.state

import androidx.annotation.DrawableRes
import antuere.domain.dto.helplines.SupportedCountry
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.util.extensions.getName

sealed interface HelplinesState {
    object Loading : HelplinesState
    data class Loaded(
        val supportedCountries: List<SupportedCountry>,
        val selectedCountry: SupportedCountry,
        val textFieldValue: UiText = selectedCountry.getName(),
        @DrawableRes val currentFlagId: Int = selectedCountry.flagId,
        val isMenuExpanded: Boolean = false,
    ) : HelplinesState
}

