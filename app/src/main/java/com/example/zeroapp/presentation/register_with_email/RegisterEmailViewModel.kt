package com.example.zeroapp.presentation.register_with_email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.usecases.SaveUserNicknameUseCase
import antuere.domain.usecases.authentication.SetUserNicknameOnServerUseCase
import antuere.domain.usecases.authentication.SignUpUseCase
import com.example.zeroapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterEmailViewModel @Inject constructor(
    private val saveUserNicknameUseCase: SaveUserNicknameUseCase,
    private val setUserNicknameOnServerUseCase: SetUserNicknameOnServerUseCase,
    private val signUpUseCase: SignUpUseCase
) : ViewModel() {

    private var _registerState = MutableLiveData<RegisterState?>()
    val registerState: LiveData<RegisterState?>
        get() = _registerState


    private val firebaseRegisterListener = object : RegisterResultListener {

        override fun registerSuccess(name: String) {
            registerSuccessful(name)
        }

        override fun registerFailed(message: String) {
            _registerState.value = RegisterState.ErrorFromFireBase(message)
        }
    }

    private fun registerSuccessful(name: String) {
        viewModelScope.launch {
            saveUserNicknameUseCase(name)
            setUserNicknameOnServerUseCase(name)
            _registerState.value = RegisterState.Successful
        }
    }


    fun onClickSignUp(email: String, password: String, confirmPassword: String, name: String) {
        if (isValidateFields(email, password, confirmPassword, name)) {
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
                _registerState.value = RegisterState.PasswordsError(R.string.passwords_error)
                false
            }
        } else {
            _registerState.value = RegisterState.EmptyFields(R.string.empty_fields)
            false
        }
    }

    fun nullifyState() {
        _registerState.value = null
    }
}