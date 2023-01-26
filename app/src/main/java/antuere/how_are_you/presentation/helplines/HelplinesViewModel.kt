package antuere.how_are_you.presentation.helplines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.dto.helplines.SupportedCountry
import antuere.domain.repository.HelplinesRepository
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.presentation.helplines.state.HelplinesIntent
import antuere.how_are_you.presentation.helplines.state.HelplinesState
import antuere.how_are_you.util.ContainerHostPlus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val settingsRepository: SettingsRepository,
) : ContainerHostPlus<HelplinesState, Nothing, HelplinesIntent>, ViewModel() {

    override val container: Container<HelplinesState, Nothing> =
        container(HelplinesState.Loading)

    init {
        intent {
            viewModelScope.launch(Dispatchers.IO) {
                val supportedCountries = helplinesRepository.getSupportedCountries()
                val selectedCountryId = settingsRepository.getSelectedCountryId()
                val selectedCountry = supportedCountries.find { it.id == selectedCountryId }!!

                reduce {
                    HelplinesState.Loaded(supportedCountries, selectedCountry)
                }
            }
        }
    }

    override fun onIntent(intent: HelplinesIntent) = intent {
        when (intent) {
            is HelplinesIntent.CountrySelected -> {
                reduce {
                    (state as HelplinesState.Loaded).copy(selectedCountry = intent.country)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    settingsRepository.saveSelectedCountryId(intent.country)
                }
            }
        }
    }
}