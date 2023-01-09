package com.example.zeroapp.presentation.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.ResetPassResultListener
import antuere.domain.usecases.authentication.ResetPasswordUseCase
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private var _resetState = MutableStateFlow<ResetPasswordState?>(null)
    val resetState: StateFlow<ResetPasswordState?>
        get() = _resetState

    private var _isShowProgressIndicator = MutableStateFlow(false)
    val isShowProgressIndicator: StateFlow<Boolean>
        get() = _isShowProgressIndicator

    private val resetPassResultListener = object : ResetPassResultListener {
        override fun resetSuccess() {
            _resetState.value =
                ResetPasswordState.Successful(UiText.StringResource(R.string.email_reset_successful))
        }

        override fun resetError(message: String) {
            _resetState.value = ResetPasswordState.ErrorFromFireBase(UiText.DefaultString(message))
        }
    }

    fun onClickResetPassword(email: String) {
        if (email.isNotEmpty()) {
            _isShowProgressIndicator.value = true
            viewModelScope.launch {
                resetPasswordUseCase(resetPassResultListener, email)
            }
        } else {
            _resetState.value =
                ResetPasswordState.EmptyFields(UiText.StringResource(R.string.empty_fields))
        }
    }

    fun nullifyState() {
        _resetState.value = null
    }

    fun resetIsShowLoginProgressIndicator() {
        _isShowProgressIndicator.value = false
    }
}