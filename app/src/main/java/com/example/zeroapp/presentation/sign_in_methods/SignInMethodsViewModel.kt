package com.example.zeroapp.presentation.sign_in_methods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.usecases.authentication.CheckCurrentAuthUseCase
import antuere.domain.usecases.RefreshRemoteDataUseCase
import antuere.domain.usecases.SaveUserNicknameUseCase
import antuere.domain.usecases.authentication.SetUserNicknameOnServerUseCase
import antuere.domain.usecases.authentication.SignInByGoogleUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInMethodsViewModel @Inject constructor(
    private val refreshRemoteDataUseCase: RefreshRemoteDataUseCase,
    private val saveUserNicknameUseCase: SaveUserNicknameUseCase,
    private val checkCurrentAuthUseCase: CheckCurrentAuthUseCase,
    private val setUserNicknameOnServerUseCase: SetUserNicknameOnServerUseCase,
    private val signInByGoogleUseCase: SignInByGoogleUseCase
) : ViewModel() {

    private var _signInState = MutableLiveData<SignInMethodsState?>()
    val signInState: LiveData<SignInMethodsState?>
        get() = _signInState


    private val googleSignInListener = object : RegisterResultListener {

        override fun registerSuccess(name: String) {
            loginSuccessful(name)
        }

        override fun registerFailed(message: String) {
            _signInState.value = SignInMethodsState.Error(message)
        }
    }

    init {
        checkCurrentAuth()
    }

    fun checkCurrentAuth() {
        viewModelScope.launch {
            if (checkCurrentAuthUseCase(Unit)) {
                _signInState.value = SignInMethodsState.UserAuthorized
            }
        }
    }

    private fun loginSuccessful(name: String) {
        viewModelScope.launch {
            setUserNicknameOnServerUseCase(name)
            saveUserNicknameUseCase(name)
            refreshRemoteDataUseCase(Unit)
            _signInState.value = SignInMethodsState.UserAuthorized
        }
    }

    fun handleResult(task: Task<GoogleSignInAccount>) {
        if (task.isSuccessful) {
            val account = task.result
            if (account != null) {
                signInWithGoogle(account)
            }
        } else {
            _signInState.value = SignInMethodsState.Error(task.exception!!.message!!)
        }
    }

    private fun signInWithGoogle(account: GoogleSignInAccount) {
        val name = account.displayName ?: account.givenName ?: "Google user"
        viewModelScope.launch {
            signInByGoogleUseCase(googleSignInListener, name)
        }
    }

    fun nullifyState() {
        _signInState.value = null
    }
}