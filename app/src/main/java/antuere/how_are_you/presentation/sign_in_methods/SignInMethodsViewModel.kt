package antuere.how_are_you.presentation.sign_in_methods

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.repository.DayRepository
import antuere.domain.repository.SettingsRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SignInMethodsViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val authenticationManager: AuthenticationManager,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private var _signInState = MutableStateFlow<SignInMethodsState?>(null)
    val signInState: StateFlow<SignInMethodsState?>
        get() = _signInState


    private val googleSignInListener = object : RegisterResultListener {

        override fun registerSuccess(name: String) {
            loginSuccessful(name)
        }

        override fun registerFailed(message: String) {
            _signInState.value = SignInMethodsState.Error(message)
        }
    }

    private fun loginSuccessful(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationManager.setUserNicknameOnServer(name)
            settingsRepository.saveUserNickname(name)
            dayRepository.refreshRemoteData()
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
        val idToken = account.idToken!!

        authenticationManager.startAuthByGoogle(
            accIdToken = idToken,
            name = name,
            registerResultListener = googleSignInListener
        )
    }

    fun nullifyState() {
        _signInState.value = null
    }
}