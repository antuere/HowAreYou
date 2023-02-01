package antuere.how_are_you.presentation.secure_entry.state


sealed interface SecureEntryIntent {
    data class NumberClicked(val number: String) : SecureEntryIntent
    object BiometricBtnClicked : SecureEntryIntent
    object SignOutBtnClicked : SecureEntryIntent
    object PinStateReset : SecureEntryIntent
}