package com.example.zeroapp.presentation.sign_in_methods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.data.remote_day_database.FirebaseApi
import antuere.domain.usecases.RefreshRemoteDataUseCase
import antuere.domain.usecases.SaveUserNicknameUseCase
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInMethodsViewModel @Inject constructor(
    private val refreshRemoteDataUseCase: RefreshRemoteDataUseCase,
    private val saveUserNicknameUseCase: SaveUserNicknameUseCase,
    val firebaseApi: FirebaseApi
) : ViewModel() {

    private var _signInState = MutableLiveData<SignInMethodsState?>()
    val signInState: LiveData<SignInMethodsState?>
        get() = _signInState

    init {
        checkCurrentAuth()
    }

    fun checkCurrentAuth() {
        if (firebaseApi.isHasUser()) {
            _signInState.value = SignInMethodsState.UserAuthorized
        }
    }

    private fun loginSuccessful(name: String) {
        viewModelScope.launch {
            firebaseApi.setUserNickname(name)
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
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        firebaseApi.auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    val name = account.displayName ?: account.givenName ?: "Google user"
                    loginSuccessful(name)
                }
            }
            .addOnFailureListener {
                _signInState.value = SignInMethodsState.Error(it.message ?: "Error")
            }
    }

    fun nullifyState() {
        _signInState.value = null
    }
}