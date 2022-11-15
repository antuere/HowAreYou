package antuere.domain.privacy_manager

interface PrivacyManager {

    val isUserAuthByBiometric: Boolean

    val isUserAuthByPinCode: Boolean

    fun resetAuthUserByBiometric()

    fun doneAuthUserByBiometric()

    fun resetAuthUserByPinCode()

    fun doneAuthUserByPinCode()

}