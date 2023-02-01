package antuere.how_are_you.presentation.helplines

import androidx.lifecycle.viewModelScope
import antuere.domain.repository.HelplinesRepository
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.presentation.helplines.state.HelplinesIntent
import antuere.how_are_you.presentation.helplines.state.HelplinesState
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.helplines.state.HelplinesSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class HelplinesViewModel @Inject constructor(
    private val helplinesRepository: HelplinesRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModelMvi<HelplinesState, HelplinesSideEffect, HelplinesIntent>() {

    override val container: Container<HelplinesState, HelplinesSideEffect> =
        container(HelplinesState.Loading)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val supportedCountries = helplinesRepository.getSupportedCountries()
            val selectedCountryId = settingsRepository.getSelectedCountryId()
            val selectedCountry = supportedCountries.find { it.id == selectedCountryId }!!

            updateState {
                HelplinesState.Loaded(supportedCountries, selectedCountry)
            }
        }
    }

    override fun onIntent(intent: HelplinesIntent) {
        when (intent) {
            is HelplinesIntent.CountrySelected -> {
                updateState {
                    (state as HelplinesState.Loaded).copy(selectedCountry = intent.country)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    settingsRepository.saveSelectedCountryId(intent.country)
                }
            }
            is HelplinesIntent.PhoneClicked -> {
                sideEffect(HelplinesSideEffect.NavigateToDialNumber(intent.phone))
            }
            is HelplinesIntent.WebsiteClicked -> {
                sideEffect(HelplinesSideEffect.NavigateToWebsite(intent.website))
            }
        }
    }
}