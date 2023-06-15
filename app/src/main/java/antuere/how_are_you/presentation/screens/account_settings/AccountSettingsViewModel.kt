package antuere.how_are_you.presentation.screens.account_settings

import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.dto.AuthMethod
import antuere.domain.dto.AuthProvider
import antuere.domain.repository.DayRepository
import antuere.domain.repository.SettingsRepository
import antuere.domain.repository.ToggleBtnRepository
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.account_settings.state.AccountSettingsIntent
import antuere.how_are_you.presentation.screens.account_settings.state.AccountSettingsSideEffect
import antuere.how_are_you.presentation.screens.account_settings.state.AccountSettingsState
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class AccountSettingsViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val authenticationManager: AuthenticationManager,
    private val toggleBtnRepository: ToggleBtnRepository,
    private val settingsRepository: SettingsRepository,
    private val signInClient: GoogleSignInClient,
) : ViewModelMvi<AccountSettingsState, AccountSettingsSideEffect, AccountSettingsIntent>() {

    override val container: Container<AccountSettingsState, AccountSettingsSideEffect> = container(
        AccountSettingsState()
    )
    private var isShowDialogSignOut = false

    init {
        getUserNickname()
        checkIsHasDayEntities()
    }

    override fun onIntent(intent: AccountSettingsIntent) {
        when (intent) {
            AccountSettingsIntent.DeleteAccountBtnClicked -> {
                updateState { state.copy(isShowAccDeleteDialog = true) }
            }

            AccountSettingsIntent.DeleteDataBtnClicked -> {
                val dialog = UIDialog(
                    title = R.string.dialog_delete_data_title,
                    desc = R.string.dialog_delete_data_desc,
                    icon = R.drawable.ic_delete,
                    positiveButton = UIDialog.UiButton(text = R.string.dialog_delete_data_positive,
                        onClick = {
                            deleteData()
                        }),
                    negativeButton = UIDialog.UiButton(text = R.string.dialog_no)
                )
                sideEffect(AccountSettingsSideEffect.Dialog(dialog))
            }

            AccountSettingsIntent.SignOutBtnClicked -> {
                if (isShowDialogSignOut) {
                    val dialog = UIDialog(
                        title = R.string.dialog_delete_local_data_title,
                        desc = R.string.dialog_delete_local_data_desc,
                        icon = R.drawable.ic_delete,
                        positiveButton = UIDialog.UiButton(text = R.string.dialog_delete_local_data_positive,
                            onClick = {
                                signOut(isSaveDayEntities = true)
                            }),
                        negativeButton = UIDialog.UiButton(text = R.string.dialog_delete_local_data_negative,
                            onClick = {
                                signOut(isSaveDayEntities = false)
                            }),
                    )
                    sideEffect(AccountSettingsSideEffect.Dialog(dialog))
                } else {
                    signOut(false)
                }
            }

            AccountSettingsIntent.StartReauthClicked -> {
                val authProvider = authenticationManager.getAuthMethod()

                if (authProvider == AuthMethod.EMAIL) {
                    updateState {
                        state.copy(
                            isShowReauthDialog = true,
                            isShowAccDeleteDialog = false
                        )
                    }
                }

                if (authProvider == AuthMethod.GOOGLE) {
                    updateState { state.copy(isShowAccDeleteDialog = false) }
                    sideEffect(AccountSettingsSideEffect.GoogleSignInDialog(signInClient))
                }
            }

            is AccountSettingsIntent.ConfirmedPasswordChanged -> {
                updateStateBlocking {
                    state.copy(
                        userEnteredPassword = intent.value, isShowErrorInTextField = false
                    )
                }
            }

            is AccountSettingsIntent.GoogleAccAdded -> {
                if (intent.task.isSuccessful) {
                    val account = intent.task.result ?: return
                    val idToken = account.idToken ?: return

                    updateState { state.copy(isShowProgressIndicator = true) }
                    authenticationManager.reAuthenticate(
                        authProvider = AuthProvider.GoogleAccount(accIdToken = idToken),
                        onSuccess = {
                            deleteAccount()
                        },
                        onFailure = {
                            val errorMessage =
                                if (it != null) UiText.String(it) else UiText.StringResource(R.string.default_error)
                            updateState { state.copy(isShowProgressIndicator = false) }
                            sideEffect(AccountSettingsSideEffect.Snackbar(errorMessage))
                        })
                } else {
                    sideEffect(
                        AccountSettingsSideEffect.Snackbar(
                            message = UiText.String(
                                intent.task.exception?.message ?: "Error with Google Authentication"
                            )
                        )
                    )
                }
            }

            AccountSettingsIntent.PasswordEntered -> {
                if (state.userEnteredPassword.isEmpty()) {
                    updateState {
                        state.copy(
                            isShowProgressIndicator = false,
                            isShowErrorInTextField = true,
                            errorMessage = UiText.StringResource(R.string.empty_password)
                        )
                    }
                } else {
                    updateState { state.copy(isShowProgressIndicator = true) }
                    authenticationManager.reAuthenticate(
                        authProvider = AuthProvider.Email(password = state.userEnteredPassword),
                        onSuccess = {
                            deleteAccount()
                        },
                        onFailure = {
                            val errorMessage =
                                if (it != null) UiText.String(it) else UiText.StringResource(R.string.password_invalid)
                            updateState {
                                state.copy(
                                    isShowProgressIndicator = false,
                                    userEnteredPassword = "",
                                    isShowErrorInTextField = true,
                                    errorMessage = errorMessage
                                )
                            }
                        }
                    )
                }
            }

            AccountSettingsIntent.ReauthPasswordDialogClosed -> {
                updateState { state.copy(isShowReauthDialog = false, userEnteredPassword = "") }
            }

            AccountSettingsIntent.AccDeleteDialogClosed -> {
                updateState { state.copy(isShowAccDeleteDialog = false, isSaveLocalData = false) }
            }

            AccountSettingsIntent.SaveLocalDataSettingChanged -> {
                updateState { state.copy(isSaveLocalData = !state.isSaveLocalData) }
            }


        }
    }

    private fun checkIsHasDayEntities() {
        viewModelScope.launch(Dispatchers.IO) {
            isShowDialogSignOut = dayRepository.getLastDay().first() != null
        }
    }

    private fun getUserNickname() {
        viewModelScope.launch(Dispatchers.IO) {
            val nickname = settingsRepository.getUserNickname().first()
            updateState {
                state.copy(
                    isLoading = false, userNickname = nickname
                )
            }
        }

    }

    private fun signOut(isSaveDayEntities: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            if (!isSaveDayEntities) {
                dayRepository.deleteAllDaysLocal()
            }
            authenticationManager.signOut()
            toggleBtnRepository.resetToggleButtonState()
            settingsRepository.resetUserNickname()

            sideEffect(AccountSettingsSideEffect.NavigateToSettings)
        }

    }

    private fun deleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationManager.deleteAccount(
                onSuccess = {
                    launch(Dispatchers.IO) {
                        if (!state.isSaveLocalData) {
                            dayRepository.deleteAllDaysLocal()
                        }
                        settingsRepository.resetUserNickname()
                        updateState {
                            state.copy(
                                isShowProgressIndicator = false, isShowReauthDialog = false
                            )
                        }
                        delay(50)

                        sideEffect(AccountSettingsSideEffect.Snackbar(UiText.StringResource(R.string.delete_acc_success)))
                        sideEffect(AccountSettingsSideEffect.NavigateToSettings)
                    }
                },
                onFailure = {
                    updateState {
                        val errorMessage =
                            if (it != null) UiText.String(it) else UiText.StringResource(R.string.default_error)
                        state.copy(
                            isShowProgressIndicator = false,
                            userEnteredPassword = "",
                            isShowErrorInTextField = true,
                            errorMessage = errorMessage
                        )
                    }
                },
            )

            delay(200)
            coroutineContext.job.children.forEach {
                it.join()
            }
        }
    }

    private fun deleteData() {
        updateState { state.copy(isShowProgressIndicator = true) }
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.deleteAllDaysRemote(
                onSuccess = {
                    launch(Dispatchers.IO) {
                        dayRepository.deleteAllDaysLocal()
                    }
                    sideEffect(AccountSettingsSideEffect.Snackbar(UiText.StringResource(R.string.delete_data_success)))
                    updateState { state.copy(isShowProgressIndicator = false) }
                },
                onFailure = {
                    val message =
                        if (it != null) UiText.String(it) else UiText.StringResource(R.string.default_error)
                    sideEffect(AccountSettingsSideEffect.Snackbar(message))
                    updateState { state.copy(isShowProgressIndicator = false) }
                }
            )

            delay(200)
            coroutineContext.job.children.forEach {
                it.join()
            }
        }
    }


}