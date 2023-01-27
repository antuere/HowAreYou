package antuere.how_are_you.presentation.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.ResetPassResultListener
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.reset_password.state.ResetPasswordIntent
import antuere.how_are_you.presentation.reset_password.state.ResetPasswordSideEffect
import antuere.how_are_you.presentation.reset_password.state.ResetPasswordState
import antuere.how_are_you.util.ContainerHostPlus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.annotation.OrbitExperimental
import org.orbitmvi.orbit.syntax.simple.blockingIntent
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@OptIn(OrbitExperimental::class)
@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager
) : ContainerHostPlus<ResetPasswordState, ResetPasswordSideEffect, ResetPasswordIntent>,
    ViewModel() {

    override val container: Container<ResetPasswordState, ResetPasswordSideEffect> =
        container(ResetPasswordState())

    private val resetPassResultListener = object : ResetPassResultListener {
        override fun resetSuccess() = intent {
            reduce { ResetPasswordState() }
            postSideEffect(
                ResetPasswordSideEffect.Snackbar(UiText.StringResource(R.string.email_reset_successful))
            )
            postSideEffect(ResetPasswordSideEffect.NavigateUp)
        }

        override fun resetError(message: String) = intent {
            reduce { state.copy(isShowProgressIndicator = false) }
            postSideEffect(
                ResetPasswordSideEffect.Snackbar(UiText.DefaultString(message))
            )
        }
    }

    override fun onIntent(intent: ResetPasswordIntent) {
        when (intent) {
            is ResetPasswordIntent.EmailChanged -> blockingIntent {
                reduce {
                    state.copy(email = intent.value)
                }
            }
            is ResetPasswordIntent.ResetBtnClicked -> intent {
                if (intent.userEmail.isNotEmpty()) {
                    reduce {
                        state.copy(isShowProgressIndicator = true)
                    }
                    viewModelScope.launch(Dispatchers.IO) {
                        authenticationManager.resetPassword(
                            email = intent.userEmail,
                            resetPassResultListener = resetPassResultListener
                        )
                    }
                } else {
                    postSideEffect(
                        ResetPasswordSideEffect.Snackbar(UiText.StringResource(R.string.empty_fields))
                    )
                }
            }
        }
    }
}