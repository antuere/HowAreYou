package com.example.zeroapp.presentation.helplines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.helplines.Helpline
import antuere.domain.dto.helplines.SupportedCountry
import antuere.domain.usecases.helplines.GetSupportedCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HelplinesViewModel @Inject constructor(
    getSupportedCountriesUseCase: GetSupportedCountriesUseCase
) : ViewModel() {

    private var _countries = MutableStateFlow<List<SupportedCountry>>(emptyList())
    val countries: StateFlow<List<SupportedCountry>>
        get() = _countries

    private var _selectedCountry = MutableStateFlow<SupportedCountry?>(null)
    val selectedCountry: StateFlow<SupportedCountry?>
        get() = _selectedCountry


    init {
        viewModelScope.launch {
            _countries.value = getSupportedCountriesUseCase(Unit)
            _selectedCountry.value = _countries.value.first()
        }
    }


    fun onCountrySelected(country: SupportedCountry) {
        _selectedCountry.value = country
    }

}