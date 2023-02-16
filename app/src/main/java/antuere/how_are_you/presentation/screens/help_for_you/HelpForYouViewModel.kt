package antuere.how_are_you.presentation.screens.help_for_you

import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.screens.help_for_you.state.HelpForYouIntent
import antuere.how_are_you.presentation.screens.help_for_you.state.HelpForYouSideEffect
import antuere.how_are_you.presentation.screens.help_for_you.state.HelpForYouState
import dagger.hilt.android.lifecycle.HiltViewModel
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class HelpForYouViewModel @Inject constructor() :
    ViewModelMvi<HelpForYouState, HelpForYouSideEffect, HelpForYouIntent>() {

    override val container: Container<HelpForYouState, HelpForYouSideEffect> =
        container(HelpForYouState())

    override fun onIntent(intent: HelpForYouIntent) {
        when (intent) {
            HelpForYouIntent.EmailCardClicked -> {
                // TODO will be implemented later
            }
            HelpForYouIntent.HelplinesCardClicked -> {
                sideEffect(HelpForYouSideEffect.NavigateToHelplines)
            }
            HelpForYouIntent.TelegramCardClicked -> {
                // TODO will be implemented later
            }
        }
    }
}