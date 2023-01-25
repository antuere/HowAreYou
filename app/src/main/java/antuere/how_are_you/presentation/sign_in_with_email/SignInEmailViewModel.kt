package antuere.how_are_you.presentation.sign_in_with_email

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.LoginResultListener
import antuere.domain.repository.DayRepository
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
class SignInEmailViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val authenticationManager: AuthenticationManager,
    private val settingsRepository: SettingsRepository
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
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.refreshRemoteData()
            delay(100)
            val userNickname = authenticationManager.getUserNickName() ?: "Unknown"
            settingsRepository.saveUserNickname(userNickname)
            _signInState.value = SignInState.Successful
        }
    }

    fun onClickSignIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            _isShowLoginProgressIndicator.value = true
            viewModelScope.launch(Dispatchers.IO) {
                authenticationManager.startAuth(
                    email = email,
                    password = password,
                    loginResultListener = firebaseLoginListener
                )
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