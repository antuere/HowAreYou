package antuere.how_are_you.presentation.screens.customization

import androidx.lifecycle.viewModelScope
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.screens.customization.state.CustomizationIntent
import antuere.how_are_you.presentation.screens.customization.state.CustomizationSideEffect
import antuere.how_are_you.presentation.screens.customization.state.CustomizationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class CustomizationViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModelMvi<CustomizationState, CustomizationSideEffect, CustomizationIntent>() {

    override val container: Container<CustomizationState, CustomizationSideEffect> =
        container(CustomizationState())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val fontSize = settingsRepository.getFontSizeDayView()
            val savedTheme = settingsRepository.getAppTheme().first()

            updateState {
                state.copy(
                    isLoading = false,
                    theme = savedTheme,
                    fontSize = fontSize
                )
            }
        }
    }


    override fun onIntent(intent: CustomizationIntent) {
        when (intent) {
            is CustomizationIntent.FontSizeChanged -> {
                updateState {
                    state.copy(fontSize = intent.newFontSize)
                }
                viewModelScope.launch(Dispatchers.IO) {
                    settingsRepository.saveFontSizeDayView(intent.newFontSize)
                }
            }

            CustomizationIntent.ThemeDialogClicked -> updateState {
                state.copy(isShowThemeDialog = true)
            }

            CustomizationIntent.ThemeDialogClose -> updateState {
                state.copy(isShowThemeDialog = false)
            }

            is CustomizationIntent.ThemeSelected -> {
                if (state.theme != intent.theme) {
                    updateState {
                        state.copy(theme = intent.theme)
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        settingsRepository.saveAppTheme(intent.theme)
                    }
                }
                updateState {
                    state.copy(isShowThemeDialog = false)
                }
            }
        }
    }

}