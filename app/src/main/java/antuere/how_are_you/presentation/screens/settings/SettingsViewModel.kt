package antuere.how_are_you.presentation.screens.settings

import android.content.Intent
import androidx.biometric.BiometricManager
import androidx.lifecycle.viewModelScope
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ViewModelMvi
import antuere.how_are_you.presentation.base.ui_biometric_dialog.IUIBiometricListener
import antuere.how_are_you.presentation.base.ui_biometric_dialog.UIBiometricDialog
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.screens.settings.state.SettingsIntent
import antuere.how_are_you.presentation.screens.settings.state.SettingsSideEffect
import antuere.how_are_you.presentation.screens.settings.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject


@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val uiBiometricDialog: UIBiometricDialog,
) : ViewModelMvi<SettingsState, SettingsSideEffect, SettingsIntent>() {

    override val container: Container<SettingsState, SettingsSideEffect> =
        container(SettingsState())

    init {
        getSettings()
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
                    sideEffect(SettingsSideEffect.HideNavBar)
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
            is SettingsIntent.AccountSettingsBtnClicked -> {
                sideEffect(SettingsSideEffect.NavigateToAccountSettings)
            }
            is SettingsIntent.PinCreationSheetClosed -> {
                updateState {
                    state.copy(isCheckedPin = intent.isPinCreated, isShowBottomSheet = false)
                }
                sideEffect(SettingsSideEffect.ShowNavBar)
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

    private fun getSettings() {
        viewModelScope.launch(Dispatchers.IO) {
            combine(
                settingsRepository.getAllSettings(),
                settingsRepository.getUserNickname(),
                settingsRepository.getPinCode()
            ) { settings, username, pinCode ->
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