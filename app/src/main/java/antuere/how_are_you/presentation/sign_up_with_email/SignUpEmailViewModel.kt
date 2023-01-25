package antuere.how_are_you.presentation.sign_up_with_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpEmailViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val authenticationManager: AuthenticationManager
) : ViewModel() {

    private var _signUpState = MutableStateFlow<SignUpState?>(null)
    val signUpState: StateFlow<SignUpState?>
        get() = _signUpState

    private var _isShowRegisterProgressIndicator = MutableStateFlow(false)
    val isShowRegisterProgressIndicator: StateFlow<Boolean>
        get() = _isShowRegisterProgressIndicator


    private val firebaseRegisterListener = object : RegisterResultListener {

        override fun registerSuccess(name: String) {
            registerSuccessful(name)
        }

        override fun registerFailed(message: String) {
            _signUpState.value = SignUpState.ErrorFromFireBase(UiText.DefaultString(message))
        }
    }

    private fun registerSuccessful(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveUserNickname(name)
            authenticationManager.setUserNicknameOnServer(name)
            _signUpState.value = SignUpState.Successful
        }
    }


    fun onClickSignUp(email: String, password: String, confirmPassword: String, name: String) {
        if (isValidateFields(email, password, confirmPassword, name)) {
            _isShowRegisterProgressIndicator.value = true
            viewModelScope.launch(Dispatchers.IO) {
                authenticationManager.startRegister(
                    email = email,
                    password = password,
                    name = name,
                    registerResultListener = firebaseRegisterListener
                )
            }
        }
    }

    private fun isValidateFields(
        email: String,
        password: String,
        confirmPassword: String,
        name: String
    ): Boolean {
        return if (email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty()) {
            if (password == confirmPassword) {
                true
            } else {
                _signUpState.value =
                    SignUpState.PasswordsError(UiText.StringResource(R.string.passwords_error))
                false
            }
        } else {
            _signUpState.value =
                SignUpState.EmptyFields(UiText.StringResource(R.string.empty_fields))
            false
        }
    }

    fun resetIsShowRegisterProgressIndicator(withDelay: Boolean = false) {
        if (withDelay) {
            viewModelScope.launch {
                delay(250)
                _isShowRegisterProgressIndicator.value = false
            }
        } else {
            _isShowRegisterProgressIndicator.value = false
        }
    }

    fun nullifyState() {
        _signUpState.value = null
    }
}