package antuere.how_are_you.presentation.reset_password

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.ResetPassResultListener
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val authenticationManager: AuthenticationManager
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
            viewModelScope.launch(Dispatchers.IO) {
                authenticationManager.resetPassword(
                    email = email,
                    resetPassResultListener = resetPassResultListener
                )
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