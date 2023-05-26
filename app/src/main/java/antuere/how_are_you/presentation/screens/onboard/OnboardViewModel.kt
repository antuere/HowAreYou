package antuere.how_are_you.presentation.screens.onboard

import androidx.lifecycle.viewModelScope
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.screens.onboard.state.OnboardIntent
import antuere.how_are_you.presentation.screens.onboard.state.OnboardSideEffect
import antuere.how_are_you.presentation.screens.onboard.state.OnboardState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class OnboardViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
) : ViewModelMvi<OnboardState, OnboardSideEffect, OnboardIntent>() {

    override val container: Container<OnboardState, OnboardSideEffect> = container(OnboardState())

    override fun onIntent(intent: OnboardIntent) {
        when (intent) {
            OnboardIntent.EnterInAppClicked -> {
                sideEffect(OnboardSideEffect.NavigateToHome)
                viewModelScope.launch(Dispatchers.IO) {
                    settingsRepository.firstLaunchCompleted()
                }
            }
        }
    }

}