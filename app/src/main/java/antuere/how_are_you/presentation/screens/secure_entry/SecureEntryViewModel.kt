package antuere.how_are_you.presentation.screens.secure_entry

import android.content.Intent
import android.provider.Settings
import androidx.biometric.BiometricManager
import androidx.lifecycle.viewModelScope
import antuere.domain.authentication_manager.AuthenticationManager
import antuere.domain.repository.DayRepository
import antuere.domain.repository.SettingsRepository
import antuere.domain.util.Constants
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.base.ui_biometric_dialog.IUIBiometricListener
import antuere.how_are_you.presentation.screens.pin_code_creation.PinCirclesState
import antuere.how_are_you.presentation.base.ui_biometric_dialog.UIBiometricDialog
import antuere.how_are_you.presentation.base.ui_compose_components.dialog.UIDialog
import antuere.how_are_you.presentation.screens.secure_entry.state.SecureEntryIntent
import antuere.how_are_you.presentation.screens.secure_entry.state.SecureEntrySideEffect
import antuere.how_are_you.presentation.screens.secure_entry.state.SecureEntryState
import antuere.how_are_you.presentation.base.ViewModelMvi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class SecureEntryViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository,
    private val dayRepository: DayRepository,
    private val authenticationManager: AuthenticationManager,
    private val uiBiometricDialog: UIBiometricDialog,
) : ViewModelMvi<SecureEntryState, SecureEntrySideEffect, SecureEntryIntent>() {

    override val container: Container<SecureEntryState, SecureEntrySideEffect> =
        container(SecureEntryState())

    private var num1: String? = null
    private var num2: String? = null
    private var num3: String? = null
    private var num4: String? = null
    private var currentNumbers = mutableListOf<String>()

    private var savedPinCode = Constants.PIN_NOT_SET
    private var currentPinCode = Constants.PIN_NOT_SET

    private var wrongPinAnimationJob: Job? = null

    init {
        updateState {
            state.copy(isShowBiometricBtn = uiBiometricDialog.deviceHasBiometricHardware)
        }
        viewModelScope.launch(Dispatchers.IO) {
            val isSetBiometricSetting = settingsRepository.getBiomAuthSetting().first()
            savedPinCode = settingsRepository.getPinCode().first()
            delay(450)

            if (isSetBiometricSetting) {
                sideEffect(SecureEntrySideEffect.BiometricDialog(uiBiometricDialog))
            }
        }
    }

    val biometricAuthStateListener = object : IUIBiometricListener {

        override fun onBiometricAuthFailed() {}

        override fun onBiometricAuthSuccess() {
            updateState {
                state.copy(pinCirclesState = PinCirclesState.CORRECT_PIN)
            }
            sideEffect(SecureEntrySideEffect.NavigateToHome)
            updateState { state.copy(pinCirclesState = PinCirclesState.NONE) }
            currentPinCode = Constants.PIN_NOT_SET
            currentNumbers.clear()
            viewModelScope.launch(Dispatchers.IO) {
                settingsRepository.saveBiomAuthSetting(isEnable = true)
            }
        }

        override fun noneEnrolled() {
            defineEnrollAction()
        }
    }

    override fun onIntent(intent: SecureEntryIntent) {
        when (intent) {
            is SecureEntryIntent.BiometricBtnClicked -> {
                sideEffect(SecureEntrySideEffect.BiometricDialog(uiBiometricDialog))
            }
            is SecureEntryIntent.NumberClicked -> {
                if (intent.number.length != 1) {
                    throw IllegalArgumentException("Invalid number: ${intent.number}")
                }

                currentNumbers.add(intent.number)
                checkPassword(currentNumbers)
            }
            is SecureEntryIntent.PinStateReset -> {
                updateState { state.copy(pinCirclesState = PinCirclesState.NONE) }
                currentPinCode = Constants.PIN_NOT_SET
                currentNumbers.clear()
            }
            is SecureEntryIntent.SignOutBtnClicked -> {
                val dialog = UIDialog(
                    title = R.string.dialog_sign_out_title,
                    desc = R.string.dialog_sign_out_desc,
                    icon = R.drawable.ic_log_out,
                    positiveButton = UIDialog.UiButton(
                        text = R.string.dialog_sign_out_positive,
                        onClick = { resetAllUserData() }
                    ),
                    negativeButton = UIDialog.UiButton(text = R.string.dialog_sign_out_negative)
                )

                sideEffect(SecureEntrySideEffect.Dialog(dialog))
            }
        }
    }


    private fun checkPassword(list: List<String>) {
        wrongPinAnimationJob?.cancel()
        when (list.size) {
            1 -> {
                num1 = list[0]
                updateState { state.copy(pinCirclesState = PinCirclesState.FIRST) }
            }
            2 -> {
                num2 = list[1]
                updateState { state.copy(pinCirclesState = PinCirclesState.SECOND) }
            }
            3 -> {
                num3 = list[2]
                updateState { state.copy(pinCirclesState = PinCirclesState.THIRD) }
            }
            4 -> {
                num4 = list[3]
                updateState { state.copy(pinCirclesState = PinCirclesState.FOURTH) }
                currentPinCode = num1 + num2 + num3 + num4

                validateEnteredPinCode(currentPinCode)
            }
            else -> throw IllegalArgumentException("Too much list size")
        }
    }

    private fun defineEnrollAction() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                putExtra(
                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.BIOMETRIC_WEAK
                )
            }
            sideEffect(SecureEntrySideEffect.BiometricNoneEnroll(enrollIntent))
        } else {
            sideEffect(
                SecureEntrySideEffect.Snackbar(UiText.StringResource(R.string.biometric_none_enroll))
            )
        }
    }

    private fun validateEnteredPinCode(pinCode: String) {
        if (pinCode == savedPinCode) {
            updateState { state.copy(pinCirclesState = PinCirclesState.CORRECT_PIN) }
            sideEffect(SecureEntrySideEffect.NavigateToHome)
            updateState { state.copy(pinCirclesState = PinCirclesState.NONE) }
            currentPinCode = Constants.PIN_NOT_SET
            currentNumbers.clear()
        } else {
            wrongPinAnimationJob = viewModelScope.launch {
                sideEffect(SecureEntrySideEffect.Snackbar(UiText.StringResource(R.string.wrong_pin_code)))
                updateState { state.copy(pinCirclesState = PinCirclesState.WRONG_PIN) }
                currentPinCode = Constants.PIN_NOT_SET
                currentNumbers.clear()

                delay(500)

                updateState { state.copy(pinCirclesState = PinCirclesState.NONE) }
            }
        }
    }

    private fun resetAllUserData() {
        viewModelScope.launch(Dispatchers.IO) {
            authenticationManager.signOut()
            dayRepository.deleteAllDaysLocal()
            settingsRepository.resetAllSettings()

            delay(100)
            sideEffect(SecureEntrySideEffect.NavigateToHome)
        }
    }
}
