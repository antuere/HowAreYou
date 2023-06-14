package antuere.how_are_you.presentation.screens.settings.state

sealed interface SettingsIntent {
    data class WorriedDialogSettingChanged(val isChecked: Boolean) : SettingsIntent
    data class PinSettingChanged(val isChecked: Boolean) : SettingsIntent
    data class BiometricAuthSettingChanged(val isChecked: Boolean) : SettingsIntent
    data class PinCreationSheetClosed(val isPinCreated: Boolean) : SettingsIntent
    object AccountSettingsBtnClicked : SettingsIntent
    object SignInBtnClicked : SettingsIntent
}