package com.example.zeroapp.presentation.helplines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.helplines.Helpline
import antuere.domain.dto.helplines.SupportedCountry
import antuere.domain.usecases.helplines.GetSupportedCountriesUseCase
import antuere.domain.usecases.user_settings.GetSelectedCountryIdUseCase
import antuere.domain.usecases.user_settings.SaveSelectedCountryIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HelplinesViewModel @Inject constructor(
    getSupportedCountriesUseCase: GetSupportedCountriesUseCase,
    getSelectedCountryIdUseCase: GetSelectedCountryIdUseCase,
    val saveSelectedCountryIdUseCase: SaveSelectedCountryIdUseCase
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

            withContext(Dispatchers.IO){
                getSelectedCountryIdUseCase(Unit).collectLatest { id ->
                    _selectedCountry.value = _countries.value.find { it.id == id }!!
                }
            }
        }
    }

    fun onCountrySelected(country: SupportedCountry) {
        _selectedCountry.value = country

        viewModelScope.launch {
            saveSelectedCountryIdUseCase(country)
        }
    }

}