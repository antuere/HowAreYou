package antuere.how_are_you.presentation.helplines.state

import antuere.domain.dto.helplines.SupportedCountry

sealed interface HelplinesIntent {
    class CountrySelected(val country: SupportedCountry) : HelplinesIntent
    class PhoneClicked(val phone: String) : HelplinesIntent
    class WebsiteClicked(val website: String) : HelplinesIntent
}

