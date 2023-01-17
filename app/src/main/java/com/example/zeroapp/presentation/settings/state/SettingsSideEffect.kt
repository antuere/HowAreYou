package com.example.zeroapp.presentation.settings.state

import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialog
import com.example.zeroapp.presentation.base.ui_text.UiText
import com.example.zeroapp.presentation.history.state.HistorySideEffect

interface SettingsSideEffect {

    object NavigateToSignIn : SettingsSideEffect

    object BiometricNoneEnroll : SettingsSideEffect

    data class Snackbar(val message: UiText) : SettingsSideEffect

    data class Dialog(val uiDialog: UIDialog) : SettingsSideEffect

    data class BiometricDialog(val dialog: UIBiometricDialog) : SettingsSideEffect

}