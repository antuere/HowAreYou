package antuere.how_are_you.presentation.sign_in_with_email

import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.LoginResultListener
import antuere.domain.repository.DayRepository
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.sign_in_with_email.state.SignInEmailIntent
import antuere.how_are_you.presentation.sign_in_with_email.state.SignInEmailSideEffect
import antuere.how_are_you.presentation.sign_in_with_email.state.SignInEmailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignInEmailViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val authenticationManager: AuthenticationManager,
    private val settingsRepository: SettingsRepository,
) : ViewModelMvi<SignInEmailState, SignInEmailSideEffect, SignInEmailIntent>() {

    override val container: Container<SignInEmailState, SignInEmailSideEffect> =
        container(SignInEmailState())


    override fun onIntent(intent: SignInEmailIntent) {
        when (intent) {
            is SignInEmailIntent.EmailChanged -> updateStateBlocking {
                state.copy(email = intent.value)
            }
            is SignInEmailIntent.PasswordChanged -> updateStateBlocking {
                state.copy(password = intent.value)
            }
            is SignInEmailIntent.ResetPassBtnClicked -> {
                sideEffect(SignInEmailSideEffect.NavigateToResetPassword)
            }
            is SignInEmailIntent.SignInBtnClicked -> {
                val email = state.email
                val password = state.password

                if (email.isNotEmpty() && password.isNotEmpty()) {
                    updateState { state.copy(isShowProgressIndicator = true) }
                    viewModelScope.launch(Dispatchers.IO) {
                        authenticationManager.startAuth(
                            email = email,
                            password = password,
                            loginResultListener = firebaseLoginListener
                        )
                    }
                } else {
                    sideEffect(SignInEmailSideEffect.Snackbar(message = UiText.StringResource(R.string.empty_fields)))
                }
            }
            is SignInEmailIntent.SignUpBtnClicked -> {
                sideEffect(SignInEmailSideEffect.NavigateToSignUp)
            }
        }
    }

    private val firebaseLoginListener = object : LoginResultListener {
        override fun loginSuccess() {
            viewModelScope.launch(Dispatchers.IO) {
                dayRepository.refreshRemoteData()
                delay(100)
                val userNickname = authenticationManager.getUserNickName() ?: "Unknown"
                settingsRepository.saveUserNickname(userNickname)

                sideEffect(SignInEmailSideEffect.NavigateToSettings)
                updateState { SignInEmailState() }
            }
        }

        override fun loginFailed(message: String) {
            updateState { state.copy(isShowProgressIndicator = false) }
            sideEffect(SignInEmailSideEffect.Snackbar(message = UiText.DefaultString(message)))
        }
    }
}