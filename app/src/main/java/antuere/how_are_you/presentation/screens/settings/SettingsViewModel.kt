package antuere.how_are_you.presentation.screens.settings

import android.content.Intent
import androidx.biometric.BiometricManager
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.repository.DayRepository
import antuere.domain.repository.SettingsRepository
import antuere.domain.repository.ToggleBtnRepository
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.base.ui_biometric_dialog.IUIBiometricListener
import antuere.how_are_you.presentation.base.ui_biometric_dialog.UIBiometricDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.screens.settings.state.SettingsIntent
import antuere.how_are_you.presentation.screens.settings.state.SettingsSideEffect
import antuere.how_are_you.presentation.screens.settings.state.SettingsState
import antuere.how_are_you.presentation.base.ViewModelMvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
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
) : ViewModelMvi<SettingsState, SettingsSideEffect, SettingsIntent>() {

    override val container: Container<SettingsState, SettingsSideEffect> =
        container(SettingsState())

    private var isShowDialogSignOut = false

    init {
        getSettings()
        checkIsHasDayEntity()
        checkBiometricsAvailable()
    }

    val biometricAuthStateListener = object : IUIBiometricListener {

        override fun onBiometricAuthFailed() {
            updateState { state.copy(isCheckedBiomAuth = false) }
        }

        override fun onBiometricAuthSuccess() {
            updateState { state.copy(isCheckedBiomAuth = true) }
            viewModelScope.launch(Dispatchers.IO) {
                settingsRepository.saveBiomAuthSetting(true)
            }
            sideEffect(
                SettingsSideEffect.Snackbar(UiText.StringResource(R.string.biom_auth_create_success))
            )
        }

        override fun noneEnrolled() {
            updateState { state.copy(isCheckedBiomAuth = false) }
            defineEnrollAction()
        }
    }

    override fun onIntent(intent: SettingsIntent) {
        when (intent) {
            is SettingsIntent.BiometricAuthSettingChanged -> {
                updateState { state.copy(isCheckedBiomAuth = intent.isChecked) }
                if (intent.isChecked) {
                    sideEffect(SettingsSideEffect.BiometricDialog(uiBiometricDialog))
                } else {
                    viewModelScope.launch(Dispatchers.IO) {
                        settingsRepository.saveBiomAuthSetting(false)
                    }
                }
            }
            is SettingsIntent.PinSettingChanged -> {
                updateState { state.copy(isCheckedPin = intent.isChecked) }
                if (intent.isChecked) {
                    updateState { state.copy(isShowBottomSheet = true) }
                    sideEffect(SettingsSideEffect.ShowBottomSheet)
                } else {
                    viewModelScope.launch(Dispatchers.IO) {
                        settingsRepository.resetPinCode()
                        settingsRepository.savePinSetting(false)
                        settingsRepository.saveBiomAuthSetting(false)
                    }
                }
            }
            is SettingsIntent.WorriedDialogSettingChanged -> {
                updateState { state.copy(isCheckedWorriedDialog = intent.isChecked) }
                viewModelScope.launch(Dispatchers.IO) {
                    settingsRepository.saveWorriedDialogSetting(intent.isChecked)
                }
            }
            is SettingsIntent.SignInBtnClicked -> sideEffect(SettingsSideEffect.NavigateToSignIn)
            is SettingsIntent.SignOutBtnClicked -> {
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
                    sideEffect(SettingsSideEffect.Dialog(dialog))
                } else {
                    signOut(false)
                }
            }
            is SettingsIntent.PinCreationSheetClosed -> {
                updateState {
                    state.copy(isCheckedPin = intent.isPinCreated, isShowBottomSheet = false)
                }
                sideEffect(SettingsSideEffect.HideBottomSheet)
                if (intent.isPinCreated) {
                    viewModelScope.launch(Dispatchers.IO) {
                        settingsRepository.savePinSetting(true)
                    }
                    sideEffect(SettingsSideEffect.Snackbar(UiText.StringResource(R.string.pin_code_create_success)))
                }
            }
        }
    }

    private fun checkBiometricsAvailable() {
        updateState {
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

    private fun getSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                settingsRepository.getAllSettings(),
                settingsRepository.getUserNickname(),
                settingsRepository.getPinCode()
            ) { settings, username, pinCode ->

                Timber.i("MVI error test : collect from combine")
                updateState {
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

    private fun defineEnrollAction() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val enrollIntent = Intent(android.provider.Settings.ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(
                    android.provider.Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK
                )
            }
            sideEffect(SettingsSideEffect.BiometricNoneEnroll(enrollIntent))
        } else {
            sideEffect(
                SettingsSideEffect.Snackbar(UiText.StringResource(R.string.biometric_none_enroll))
            )
        }
    }
}