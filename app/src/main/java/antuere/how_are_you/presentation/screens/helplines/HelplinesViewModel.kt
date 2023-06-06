package antuere.how_are_you.presentation.screens.helplines

import androidx.lifecycle.viewModelScope
import antuere.data.R
import antuere.domain.repository.HelplinesRepository
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.helplines.state.HelplinesIntent
import antuere.how_are_you.presentation.screens.helplines.state.HelplinesSideEffect
import antuere.how_are_you.presentation.screens.helplines.state.HelplinesState
import antuere.how_are_you.util.extensions.getName
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
            val selectedCountry =
                supportedCountries.find { it.id == selectedCountryId } ?: return@launch

            updateState {
                HelplinesState.Loaded(supportedCountries, selectedCountry)
            }
        }
    }

    override fun onIntent(intent: HelplinesIntent) {
        when (intent) {
            is HelplinesIntent.CountrySelected -> {
                updateState {
                    (state as HelplinesState.Loaded).copy(
                        selectedCountry = intent.country,
                        textFieldValue = intent.country.getName(),
                        currentFlagId = intent.country.flagId,
                        isMenuExpanded = false
                    )
                }
                sideEffect(HelplinesSideEffect.ScrollToTop)
                viewModelScope.launch(Dispatchers.IO) {
                    settingsRepository.saveSelectedCountryId(intent.country)
                }
            }

            is HelplinesIntent.CountryFieldChanged -> {
                val lowerCaseValue = intent.value.lowercase()
                val foundCountry = intent.countriesMap[lowerCaseValue]
                val flagId = foundCountry?.flagId ?: R.drawable.flag_plug

                updateStateBlocking {
                    (state as HelplinesState.Loaded).copy(
                        textFieldValue = UiText.String(intent.value),
                        currentFlagId = flagId
                    )
                }

            }

            HelplinesIntent.CountyMenuClicked -> {
                val currentState = (state as HelplinesState.Loaded)
                updateState {
                    currentState.copy(
                        isMenuExpanded = !currentState.isMenuExpanded,
                    )
                }
            }

            HelplinesIntent.CountyMenuDismissed -> {
                val currentState = (state as HelplinesState.Loaded)
                updateState {
                    currentState.copy(
                        textFieldValue = currentState.selectedCountry.getName(),
                        currentFlagId = currentState.selectedCountry.flagId,
                        isMenuExpanded = false
                    )
                }
            }

            is HelplinesIntent.HelplineClicked -> {
                sideEffect(HelplinesSideEffect.ScrollToCenterItem(intent.itemIndex))
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