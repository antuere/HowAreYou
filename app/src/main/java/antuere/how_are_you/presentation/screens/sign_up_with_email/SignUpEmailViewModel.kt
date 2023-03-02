package antuere.how_are_you.presentation.screens.sign_up_with_email

import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.repository.DayRepository
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.sign_up_with_email.state.SignUpEmailIntent
import antuere.how_are_you.presentation.screens.sign_up_with_email.state.SignUpEmailSideEffect
import antuere.how_are_you.presentation.screens.sign_up_with_email.state.SignUpEmailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SignUpEmailViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val authenticationManager: AuthenticationManager,
    private val dayRepository: DayRepository,
) : ViewModelMvi<SignUpEmailState, SignUpEmailSideEffect, SignUpEmailIntent>() {

    override val container: Container<SignUpEmailState, SignUpEmailSideEffect> =
        container(SignUpEmailState())

    override fun onIntent(intent: SignUpEmailIntent) {
        when (intent) {
            is SignUpEmailIntent.ConfirmPasswordChanged -> updateStateBlocking {
                state.copy(confirmPassword = intent.value)
            }
            is SignUpEmailIntent.EmailChanged -> updateStateBlocking {
                state.copy(email = intent.value)
            }
            is SignUpEmailIntent.NicknameChanged -> updateStateBlocking {
                state.copy(nickName = intent.value)
            }
            is SignUpEmailIntent.PasswordChanged -> updateStateBlocking {
                state.copy(password = intent.value)
            }
            is SignUpEmailIntent.SignInBtnClicked -> {
                if (isValidateFields(
                        state.email,
                        state.password,
                        state.confirmPassword,
                        state.nickName
                    )
                ) {
                    updateState { state.copy(isShowProgressIndicator = true) }
                    viewModelScope.launch(Dispatchers.IO) {
                        authenticationManager.startRegister(
                            email = state.email,
                            password = state.password,
                            name = state.nickName,
                            registerResultListener = firebaseRegisterListener
                        )
                    }
                }
            }
        }
    }

    private val firebaseRegisterListener = object : RegisterResultListener {

        override fun registerSuccess(name: String) {
            viewModelScope.launch(Dispatchers.IO) {
                authenticationManager.setUserNicknameOnServer(name)
                settingsRepository.saveUserNickname(name)
                delay(150)
                dayRepository.insertLocalDaysToRemote()

                sideEffect(SignUpEmailSideEffect.NavigateToSettings)
                updateState { SignUpEmailState() }
            }
        }

        override fun registerFailed(message: String) {
            updateState { state.copy(isShowProgressIndicator = false) }
            sideEffect(SignUpEmailSideEffect.Snackbar(message = UiText.DefaultString(message)))
        }
    }

    private fun isValidateFields(
        email: String,
        password: String,
        confirmPassword: String,
        name: String,
    ): Boolean {
        return if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
            if (password == confirmPassword) {
                true
            } else {
                sideEffect(SignUpEmailSideEffect.Snackbar(message = UiText.StringResource(R.string.passwords_error)))
                false
            }
        } else {
            sideEffect(SignUpEmailSideEffect.Snackbar(message = UiText.StringResource(R.string.empty_fields)))
            false
        }
    }
}