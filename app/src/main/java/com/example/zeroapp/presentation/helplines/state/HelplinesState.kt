package com.example.zeroapp.presentation.helplines.state

import antuere.domain.dto.helplines.SupportedCountry

sealed interface HelplinesState {
    object Loading : HelplinesState
    data class Loaded(
        val supportedCountries: List<SupportedCountry>,
        val selectedCountry: SupportedCountry
    ) : HelplinesState
}
