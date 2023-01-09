package com.example.zeroapp.presentation.sign_in_with_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.LoginResultListener
import antuere.domain.usecases.authentication.GetUserNameFromServerUseCase
import antuere.domain.usecases.authentication.SignInUseCase
import antuere.domain.usecases.days_entities.RefreshRemoteDataUseCase
import antuere.domain.usecases.user_settings.SaveUserNicknameUseCase
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInEmailViewModel @Inject constructor(
    private val refreshRemoteDataUseCase: RefreshRemoteDataUseCase,
    private val saveUserNicknameUseCase: SaveUserNicknameUseCase,
    private val getUserNameFromServerUseCase: GetUserNameFromServerUseCase,
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private var _signInState = MutableStateFlow<SignInState?>(null)
    val signInState: StateFlow<SignInState?>
        get() = _signInState

    private var _isShowLoginProgressIndicator = MutableStateFlow(false)
    val isShowLoginProgressIndicator: StateFlow<Boolean>
        get() = _isShowLoginProgressIndicator

    private val firebaseLoginListener = object : LoginResultListener {

        override fun loginSuccess() {
            loginSuccessful()
        }

        override fun loginFailed(message: String) {
            _signInState.value = SignInState.ErrorFromFireBase(UiText.DefaultString(message))
        }

    }

    private fun loginSuccessful() {
        viewModelScope.launch {
            refreshRemoteDataUseCase(Unit)
            delay(100)
            val userNickname = getUserNameFromServerUseCase(Unit)
            saveUserNicknameUseCase(userNickname ?: "Unknown")
            _signInState.value = SignInState.Successful
        }
    }

    fun onClickSignIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            _isShowLoginProgressIndicator.value = true
            viewModelScope.launch {
                signInUseCase(firebaseLoginListener, email, password)
            }
        } else {
            _signInState.value =
                SignInState.EmptyFields(UiText.StringResource(R.string.empty_fields))
        }
    }

    fun nullifyState() {
        _signInState.value = null
    }

    fun resetIsShowLoginProgressIndicator(withDelay: Boolean = false) {
        if (withDelay) {
            viewModelScope.launch {
                delay(250)
                _isShowLoginProgressIndicator.value = false
            }
        } else {
            _isShowLoginProgressIndicator.value = false
        }
    }
}