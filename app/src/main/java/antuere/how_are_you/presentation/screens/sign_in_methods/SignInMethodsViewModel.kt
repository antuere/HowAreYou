package antuere.how_are_you.presentation.screens.sign_in_methods

import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.authentication_manager.RegisterResultListener
import antuere.domain.repository.DayRepository
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.sign_in_methods.state.SignInMethodsIntent
import antuere.how_are_you.presentation.screens.sign_in_methods.state.SignInMethodsSideEffect
import antuere.how_are_you.presentation.screens.sign_in_methods.state.SignInMethodsState
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class SignInMethodsViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val authenticationManager: AuthenticationManager,
    private val settingsRepository: SettingsRepository,
    private val signInClient: GoogleSignInClient,
) : ViewModelMvi<SignInMethodsState, SignInMethodsSideEffect, SignInMethodsIntent>() {

    override val container: Container<SignInMethodsState, SignInMethodsSideEffect> =
        container(SignInMethodsState())

    override fun onIntent(intent: SignInMethodsIntent) {
        when (intent) {
            SignInMethodsIntent.EmailMethodClicked -> {
                sideEffect(SignInMethodsSideEffect.NavigateToEmailMethod)
            }

            SignInMethodsIntent.GoogleMethodClicked -> {
                sideEffect(SignInMethodsSideEffect.GoogleSignInDialog(signInClient))
            }

            is SignInMethodsIntent.GoogleAccAdded -> {
                updateState { state.copy(isLoading = true) }

                if (intent.task.isSuccessful) {
                    val account = intent.task.result ?: return
                    signInWithGoogle(account)
                } else {
                    updateState { state.copy(isLoading = false) }
                    sideEffect(
                        SignInMethodsSideEffect.Snackbar(
                            message = UiText.String(
                                intent.task.exception?.message ?: "Error with Google Authentication"
                            )
                        )
                    )
                }
            }

            is SignInMethodsIntent.PrivacyPolicyClicked -> {
                sideEffect(
                    SignInMethodsSideEffect.NavigateToPrivacyPolicy(
                        intent.url
                    )
                )
            }
        }
    }

    private val googleSignInListener = object : RegisterResultListener {

        override fun registerSuccess(name: String) {
            viewModelScope.launch(Dispatchers.IO) {
                val isHasThisAccountOnServer = authenticationManager.isHasThisAccountOnServer()
                if (isHasThisAccountOnServer) {
                    dayRepository.refreshRemoteData()
                } else {
                    authenticationManager.setUserNicknameOnServer(name)
                    delay(150)
                    dayRepository.insertLocalDaysToRemote()
                }
                settingsRepository.saveUserNickname(name)

                updateState { state.copy(isLoading = false) }
                delay(20)
                sideEffect(SignInMethodsSideEffect.NavigateUp)
            }
        }

        override fun registerFailed(message: String) {
            sideEffect(SignInMethodsSideEffect.Snackbar(message = UiText.String(message)))
        }
    }

    private fun signInWithGoogle(account: GoogleSignInAccount) {
        val name = account.displayName ?: account.givenName ?: "Google user"
        val idToken = account.idToken ?: return

        authenticationManager.startAuthByGoogle(
            accIdToken = idToken,
            name = name,
            registerResultListener = googleSignInListener
        )
    }
}