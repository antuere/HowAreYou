package com.example.zeroapp.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.data.remoteDataBase.FirebaseApi
import antuere.domain.usecases.UpdateFromFireBaseUseCase
import com.example.zeroapp.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val updateFromFireBaseUseCase: UpdateFromFireBaseUseCase,
    private val firebaseApi: FirebaseApi
) : ViewModel() {


    private var _loginState = MutableLiveData<LoginState?>()
    val loginState: LiveData<LoginState?>
        get() = _loginState

    private fun loginSuccessful() {
        viewModelScope.launch {
            updateFromFireBaseUseCase.invoke(Unit)
        }
    }

    fun onClickSignIn(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            firebaseApi.auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { signInTask ->
                    if (signInTask.isSuccessful) {
                        _loginState.value = LoginState.Successful
                        loginSuccessful()
                    }
                }.addOnFailureListener {
                    _loginState.value = LoginState.ErrorFromFireBase(it.message!!)
                }
        } else {
            _loginState.value = LoginState.EmptyFields(R.string.empty_fields)
        }
    }

    fun stateReset() {
        _loginState.value = null
    }

    fun checkCurrentAuth() {
        if (firebaseApi.auth.currentUser != null) {
            _loginState.value = LoginState.Successful
            loginSuccessful()
        }
    }
}