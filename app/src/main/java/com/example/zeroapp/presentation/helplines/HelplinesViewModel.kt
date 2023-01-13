package com.example.zeroapp.presentation.helplines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.helplines.Helpline
import antuere.domain.dto.helplines.SupportedCountry
import antuere.domain.usecases.helplines.GetSupportedCountriesUseCase
import antuere.domain.usecases.user_settings.GetSelectedCountryIdUseCase
import antuere.domain.usecases.user_settings.SaveSelectedCountryIdUseCase
import com.example.zeroapp.presentation.helplines.state.HelplinesSideEffect
import com.example.zeroapp.presentation.helplines.state.HelplinesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HelplinesViewModel @Inject constructor(
    getSupportedCountriesUseCase: GetSupportedCountriesUseCase,
    getSelectedCountryIdUseCase: GetSelectedCountryIdUseCase,
    val saveSelectedCountryIdUseCase: SaveSelectedCountryIdUseCase
) : ContainerHost<HelplinesState, HelplinesSideEffect>, ViewModel() {

    override val container: Container<HelplinesState, HelplinesSideEffect> =
        container(HelplinesState.Loading)

    init {
        intent {
            viewModelScope.launch(Dispatchers.IO){
                val supportedCountries = getSupportedCountriesUseCase(Unit)
                val selectedCountryId = getSelectedCountryIdUseCase(Unit).first()
                val selectedCountry = supportedCountries.find { it.id == selectedCountryId }!!

                reduce {
                    HelplinesState.Loaded(supportedCountries, selectedCountry)
                }
            }
        }
    }

    fun onCountrySelected(country: SupportedCountry) = intent {
        reduce {
            (state as HelplinesState.Loaded).copy(selectedCountry = country)
        }
        viewModelScope.launch {
            saveSelectedCountryIdUseCase(country)
        }
    }
}