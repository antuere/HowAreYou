package antuere.how_are_you.presentation.settings.state

sealed interface SettingsIntent {
    data class WorriedDialogSettingChanged(val isChecked: Boolean) : SettingsIntent
    data class PinSettingChanged(val isChecked: Boolean) : SettingsIntent
    data class BiometricAuthSettingChanged(val isChecked: Boolean) : SettingsIntent
    data class PinCreationSheetClosed(val isPinCreated: Boolean) : SettingsIntent
    object SignOutBtnClicked : SettingsIntent
    object SignInBtnClicked : SettingsIntent
}