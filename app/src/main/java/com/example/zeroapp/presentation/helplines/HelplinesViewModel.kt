package com.example.zeroapp.presentation.helplines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.helplines.SupportedCountry
import antuere.domain.repository.HelplinesRepository
import antuere.domain.repository.SettingsRepository
import com.example.zeroapp.presentation.helplines.state.HelplinesSideEffect
import com.example.zeroapp.presentation.helplines.state.HelplinesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HelplinesViewModel @Inject constructor(
    private val helplinesRepository: HelplinesRepository,
    private val settingsRepository: SettingsRepository
) : ContainerHost<HelplinesState, HelplinesSideEffect>, ViewModel() {

    override val container: Container<HelplinesState, HelplinesSideEffect> =
        container(HelplinesState.Loading)

    init {
        intent {
            viewModelScope.launch(Dispatchers.IO) {
                val supportedCountries = helplinesRepository.getSupportedCountries()
                val selectedCountryId = settingsRepository.getSelectedCountryId().first()
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
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveSelectedCountryId(country)
        }
    }
}