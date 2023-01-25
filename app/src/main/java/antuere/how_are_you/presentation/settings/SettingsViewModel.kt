package antuere.how_are_you.presentation.settings

import android.content.Intent
import androidx.biometric.BiometricManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.dto.Settings
import antuere.domain.repository.DayRepository
import antuere.domain.repository.SettingsRepository
import antuere.domain.repository.ToggleBtnRepository
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.base.ui_biometric_dialog.IUIBiometricListener
import antuere.how_are_you.presentation.base.ui_biometric_dialog.UIBiometricDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.settings.state.SettingsSideEffect
import antuere.how_are_you.presentation.settings.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val dayRepository: DayRepository,
    private val settingsRepository: SettingsRepository,
    private val toggleBtnRepository: ToggleBtnRepository,
    private val authenticationManager: AuthenticationManager,
    private val uiBiometricDialog: UIBiometricDialog,
) : ContainerHost<SettingsState, SettingsSideEffect>, ViewModel() {

    override val container: Container<SettingsState, SettingsSideEffect> =
        container(SettingsState())

    private var isShowDialogSignOut = false

    init {
        getSettings()
        checkIsHasDayEntity()
        checkBiometricsAvailable()
    }

    val biometricAuthStateListener = object : IUIBiometricListener {

        override fun onBiometricAuthFailed() = intent {
            reduce {
                state.copy(isCheckedBiomAuth = false)
            }
        }

        override fun onBiometricAuthSuccess() = intent {
            reduce {
                state.copy(isCheckedBiomAuth = true)
            }
            withContext(Dispatchers.IO) {
                settingsRepository.saveBiomAuthSetting(true)
            }
            postSideEffect(
                SettingsSideEffect.Snackbar(UiText.StringResource(R.string.biom_auth_create_success))
            )
        }

        override fun noneEnrolled() = intent {
            reduce {
                state.copy(isCheckedBiomAuth = false)
            }

            defineEnrollAction()
        }
    }

    fun onClickSignIn() = intent {
        postSideEffect(SettingsSideEffect.NavigateToSignIn)
    }

    fun onClickSignOut() = intent {
        if (isShowDialogSignOut) {
            val dialog = UIDialog(
                title = R.string.dialog_delete_local_data_title,
                desc = R.string.dialog_delete_local_data_desc,
                icon = R.drawable.ic_delete_black,
                positiveButton = UIDialog.UiButton(
                    text = R.string.dialog_delete_local_data_positive,
                    onClick = {
                        signOut(isSaveDayEntities = true)
                    }),
                negativeButton = UIDialog.UiButton(
                    text = R.string.dialog_delete_local_data_negative,
                    onClick = {
                        signOut(isSaveDayEntities = false)
                    }),
            )
            postSideEffect(SettingsSideEffect.Dialog(dialog))
        } else {
            signOut(false)
        }
    }

    fun onChangeWorriedDialogSetting(isShowWorriedDialog: Boolean) = intent {
        reduce {
            state.copy(isCheckedWorriedDialog = isShowWorriedDialog)
        }

        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.saveWorriedDialogSetting(isShowWorriedDialog)
        }
    }

    fun onChangePinSetting(isEnablePin: Boolean) = intent {
        reduce {
            state.copy(isCheckedPin = isEnablePin)
        }

        if (isEnablePin) {
            postSideEffect(SettingsSideEffect.ShowBottomSheet)
        } else {
            resetPinCodeAuth()

            withContext(Dispatchers.IO) {
                settingsRepository.savePinSetting(false)
                settingsRepository.saveBiomAuthSetting(false)
            }
        }
    }

    fun onChangeBiomAuthSetting(isEnableBiom: Boolean) = intent {
        reduce {
            state.copy(isCheckedBiomAuth = isEnableBiom)
        }
        if (isEnableBiom) {
            postSideEffect(SettingsSideEffect.BiometricDialog(uiBiometricDialog))
        } else {
            withContext(Dispatchers.IO) {
                settingsRepository.saveBiomAuthSetting(false)
            }
        }
    }

    fun onHandlePinCreationResult(isCreated: Boolean) = intent {
        reduce {
            state.copy(isCheckedPin = isCreated)
        }
        if (isCreated) {
            withContext(Dispatchers.IO) {
                settingsRepository.savePinSetting(true)
            }
            postSideEffect(SettingsSideEffect.Snackbar(UiText.StringResource(R.string.pin_code_create_success)))
        }
    }

    private fun checkBiometricsAvailable() = intent {
        reduce {
            state.copy(isEnableBiomAuthOnDevice = uiBiometricDialog.deviceHasBiometricHardware)
        }
    }

    private fun checkIsHasDayEntity() {
        viewModelScope.launch(Dispatchers.IO) {
            dayRepository.getLastDay().collectLatest {
                isShowDialogSignOut = it != null
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
        }
    }

    private fun getSettings() = intent {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                settingsRepository.getAllSettings(),
                settingsRepository.getUserNickname(),
                settingsRepository.getPinCode()
            ) { settings, username, pinCode ->

                Timber.i("MVI error test : collect from combine")
                reduce {
                    state.copy(
                        isLoading = false,
                        isCheckedWorriedDialog = settings.isShowWorriedDialog,
                        isCheckedPin = settings.isPinCodeEnabled,
                        isCheckedBiomAuth = settings.isBiometricEnabled,
                        userNickname = username,
                        userPinCode = pinCode
                    )
                }
            }.collect()
        }
    }

    private fun defineEnrollAction() = intent {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val enrollIntent = Intent(android.provider.Settings.ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(
                    android.provider.Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK
                )
            }
            postSideEffect(SettingsSideEffect.BiometricNoneEnroll(enrollIntent))
        } else {
            postSideEffect(
                SettingsSideEffect.Snackbar(UiText.StringResource(R.string.biometric_none_enroll))
            )
        }
    }

    private fun resetPinCodeAuth() {
        viewModelScope.launch(Dispatchers.IO) {
            settingsRepository.resetPinCode()
        }
    }
}