package com.example.zeroapp.presentation.sign_up_with_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.usecases.user_settings.SaveUserNicknameUseCase
import antuere.domain.usecases.authentication.SetUserNicknameOnServerUseCase
import antuere.domain.usecases.authentication.SignUpUseCase
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpEmailViewModel @Inject constructor(
    private val saveUserNicknameUseCase: SaveUserNicknameUseCase,
    private val setUserNicknameOnServerUseCase: SetUserNicknameOnServerUseCase,
    private val signUpUseCase: SignUpUseCase
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
        viewModelScope.launch {
            saveUserNicknameUseCase(name)
            setUserNicknameOnServerUseCase(name)
            _signUpState.value = SignUpState.Successful
        }
    }


    fun onClickSignUp(email: String, password: String, confirmPassword: String, name: String) {
        if (isValidateFields(email, password, confirmPassword, name)) {
            _isShowRegisterProgressIndicator.value = true
            viewModelScope.launch {
                signUpUseCase(firebaseRegisterListener, email, password, name)
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