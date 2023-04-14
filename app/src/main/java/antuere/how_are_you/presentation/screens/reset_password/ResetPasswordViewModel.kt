package antuere.how_are_you.presentation.screens.reset_password

import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.ResetPassResultListener
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.reset_password.state.ResetPasswordIntent
import antuere.how_are_you.presentation.screens.reset_password.state.ResetPasswordSideEffect
import antuere.how_are_you.presentation.screens.reset_password.state.ResetPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager,
) : ViewModelMvi<ResetPasswordState, ResetPasswordSideEffect, ResetPasswordIntent>() {

    override val container: Container<ResetPasswordState, ResetPasswordSideEffect> =
        container(ResetPasswordState())

    private val resetPassResultListener = object : ResetPassResultListener {
        override fun resetSuccess() {
            updateState { ResetPasswordState() }
            sideEffect(
                ResetPasswordSideEffect.Snackbar(UiText.StringResource(R.string.email_reset_successful))
            )
            sideEffect(ResetPasswordSideEffect.NavigateUp)
        }

        override fun resetError(message: String) {
            updateState { state.copy(isShowProgressIndicator = false) }
            sideEffect(
                ResetPasswordSideEffect.Snackbar(message = UiText.DefaultString(message))
            )
        }
    }

    override fun onIntent(intent: ResetPasswordIntent) {
        when (intent) {
            is ResetPasswordIntent.EmailChanged -> updateStateBlocking {
                state.copy(email = intent.value)
            }
            is ResetPasswordIntent.ResetBtnClicked -> {
                sideEffect(ResetPasswordSideEffect.ClearFocus)
                if (state.email.isNotEmpty()) {
                    updateState {
                        state.copy(isShowProgressIndicator = true)
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        authenticationManager.resetPassword(
                            email = state.email,
                            resetPassResultListener = resetPassResultListener
                        )
                    }
                } else {
                    sideEffect(
                        ResetPasswordSideEffect.Snackbar(UiText.StringResource(R.string.empty_fields))
                    )
                }
            }
        }
    }
}