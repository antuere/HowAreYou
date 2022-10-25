package com.example.zeroapp.presentation.reset_password

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import antuere.data.remote_day_database.FirebaseApi
import com.example.zeroapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val firebaseApi: FirebaseApi
) : ViewModel() {

    private var _resetState = MutableLiveData<ResetPasswordState?>()
    val resetState: LiveData<ResetPasswordState?>
        get() = _resetState


    fun onClickResetPassword(email: String) {
        if (email.isNotEmpty()) {
            firebaseApi.auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { resetTask ->
                    if (resetTask.isSuccessful) {
                        _resetState.value = ResetPasswordState.Successful(R.string.email_reset_successful)
                    }
                }
                .addOnFailureListener {
                    _resetState.value = ResetPasswordState.ErrorFromFireBase(it.message!!)
                }
        } else {
            _resetState.value = ResetPasswordState.EmptyFields(R.string.empty_fields)
        }

    }

    fun navigationDone() {
        _resetState.value = null
    }

}