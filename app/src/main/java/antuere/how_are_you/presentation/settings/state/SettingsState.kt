package antuere.how_are_you.presentation.settings.state

import antuere.domain.util.Constants


data class SettingsState(
    val isLoading: Boolean = true,
    val isCheckedWorriedDialog: Boolean = true,
    val isCheckedPin: Boolean = false,
    val isEnableBiomAuthOnDevice : Boolean = true,
    val isCheckedBiomAuth: Boolean = false,
    val userPinCode: String = "",
    val userNickname: String = Constants.USER_NOT_AUTH
)