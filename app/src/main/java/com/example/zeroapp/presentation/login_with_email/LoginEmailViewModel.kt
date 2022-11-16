package com.example.zeroapp.presentation.login_with_email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.LoginResultListener
import antuere.domain.usecases.authentication.CheckCurrentAuthUseCase
import antuere.domain.usecases.authentication.GetUserNameFromServerUseCase
import antuere.domain.usecases.authentication.SignInUseCase
import antuere.domain.usecases.days_entities.RefreshRemoteDataUseCase
import antuere.domain.usecases.user_settings.SaveUserNicknameUseCase
import com.example.zeroapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginEmailViewModel @Inject constructor(
    private val refreshRemoteDataUseCase: RefreshRemoteDataUseCase,
    private val saveUserNicknameUseCase: SaveUserNicknameUseCase,
    private val checkCurrentAuthUseCase: CheckCurrentAuthUseCase,
    private val getUserNameFromServerUseCase: GetUserNameFromServerUseCase,
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private var _loginState = MutableLiveData<LoginState?>()
    val loginState: LiveData<LoginState?>
        get() = _loginState

    private var _isShowLoginProgressIndicator = MutableLiveData(false)
    val isShowLoginProgressIndicator: LiveData<Boolean>
        get() = _isShowLoginProgressIndicator

    private val firebaseLoginListener = object : LoginResultListener {

        override fun loginSuccess() {
            loginSuccessful()
        }

        override fun loginFailed(message: String) {
            _loginState.value = LoginState.ErrorFromFireBase(message)
        }

    }

    private fun loginSuccessful() {
        viewModelScope.launch {
            refreshRemoteDataUseCase(Unit)
            delay(100)
            val userNickname = getUserNameFromServerUseCase(Unit)
            saveUserNicknameUseCase(userNickname ?: "Unknown")
            _loginState.value = LoginState.Successful
        }
    }

    fun onClickSignIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            _isShowLoginProgressIndicator.value = true
            viewModelScope.launch {
                signInUseCase(firebaseLoginListener, email, password)
            }
        } else {
            _loginState.value = LoginState.EmptyFields(R.string.empty_fields)
        }
    }

    fun nullifyState() {
        _loginState.value = null
    }

    fun resetIsShowLoginProgressIndicator(withDelay: Boolean = false) {
        if(withDelay){
            viewModelScope.launch {
                delay(250)
                _isShowLoginProgressIndicator.value = false
            }
        } else {
            _isShowLoginProgressIndicator.value = false
        }
    }

    fun checkCurrentAuth() {
        viewModelScope.launch {
            if (checkCurrentAuthUseCase(Unit)) {
                _loginState.value = LoginState.Successful

                viewModelScope.launch {
                    refreshRemoteDataUseCase(Unit)
                }
            }
        }
    }
}