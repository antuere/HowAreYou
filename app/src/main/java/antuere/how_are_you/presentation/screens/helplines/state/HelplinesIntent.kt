package antuere.how_are_you.presentation.screens.helplines.state

import antuere.domain.dto.helplines.SupportedCountry

sealed interface HelplinesIntent {
    class CountrySelected(val country: SupportedCountry) : HelplinesIntent
    class CountryFieldChanged(
        val value: String,
        val countriesMap: Map<String, SupportedCountry>,
    ) : HelplinesIntent

    class PhoneClicked(val phone: String) : HelplinesIntent
    class HelplineClicked(val itemIndex: Int) : HelplinesIntent
    class WebsiteClicked(val website: String) : HelplinesIntent
    object CountyMenuClicked : HelplinesIntent
    object CountyMenuDismissed : HelplinesIntent
}

