package com.example.zeroapp.presentation.reset_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.ResetPassResultListener
import antuere.domain.usecases.authentication.ResetPasswordUseCase
import com.example.zeroapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val resetPasswordUseCase: ResetPasswordUseCase
) : ViewModel() {

    private var _resetState = MutableLiveData<ResetPasswordState?>()
    val resetState: LiveData<ResetPasswordState?>
        get() = _resetState


    private val resetPassResultListener = object : ResetPassResultListener {
        override fun resetSuccess() {
            _resetState.value = ResetPasswordState.Successful(R.string.email_reset_successful)
        }

        override fun resetError(message: String) {
            _resetState.value = ResetPasswordState.ErrorFromFireBase(message)
        }
    }

    fun onClickResetPassword(email: String) {
        if (email.isNotEmpty()) {
            viewModelScope.launch {
                resetPasswordUseCase(resetPassResultListener, email)
            }
        } else {
            _resetState.value = ResetPasswordState.EmptyFields(R.string.empty_fields)
        }
    }

    fun nullifyState() {
        _resetState.value = null
    }

}