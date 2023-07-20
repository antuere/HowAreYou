package antuere.how_are_you.presentation.screens.day_customization

import androidx.lifecycle.viewModelScope
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.screens.day_customization.state.DayCustomizationIntent
import antuere.how_are_you.presentation.screens.day_customization.state.DayCustomizationSideEffect
import antuere.how_are_you.presentation.screens.day_customization.state.DayCustomizationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DayCustomizationViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModelMvi<DayCustomizationState, DayCustomizationSideEffect, DayCustomizationIntent>() {

    override val container: Container<DayCustomizationState, DayCustomizationSideEffect> =
        container(DayCustomizationState())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val fontSize = settingsRepository.getFontSizeDayView()
            updateState {
                state.copy(
                    isLoading = false,
                    fontSize = fontSize
                )
            }
        }
    }


    override fun onIntent(intent: DayCustomizationIntent) {
        when (intent) {
            is DayCustomizationIntent.FontSizeChanged -> {
                Timber.i("Font size check: saved to rep")
                updateState {
                    state.copy(fontSize = intent.newFontSize)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    settingsRepository.saveFontSizeDayView(intent.newFontSize)
                }
            }

        }
    }

}