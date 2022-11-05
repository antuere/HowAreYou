package com.example.zeroapp.presentation.register_with_email

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import antuere.data.remote_day_database.FirebaseApi
import com.example.zeroapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterEmailViewModel @Inject constructor(
    private val firebaseApi: FirebaseApi
) : ViewModel() {

    private var _registerState = MutableLiveData<RegisterState?>()
    val registerState: LiveData<RegisterState?>
        get() = _registerState


    fun onClickSignUp(email: String, password: String, confirmPassword: String, name: String) {
        if (isValidateFields(email, password, confirmPassword, name)) {
            firebaseApi.auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { signUpTask ->
                    if (signUpTask.isSuccessful) {
                        _registerState.value = RegisterState.Successful
                        firebaseApi.setUserNickname(name)
                    }
                }.addOnFailureListener {
                    _registerState.value = RegisterState.ErrorFromFireBase(it.message!!)
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


    fun nullifyState () {
        _registerState.value = null
    }
}