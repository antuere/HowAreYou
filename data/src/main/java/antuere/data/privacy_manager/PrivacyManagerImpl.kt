package antuere.data.privacy_manager

import antuere.domain.privacy_manager.PrivacyManager

class PrivacyManagerImpl : PrivacyManager {

    private var _isUserAuthByBiometric = false
    override val isUserAuthByBiometric: Boolean
        get() = _isUserAuthByBiometric

    private var _isUserAuthByPinCode = false
    override val isUserAuthByPinCode: Boolean
        get() = _isUserAuthByPinCode

    override fun resetAuthUserByBiometric() {
        _isUserAuthByBiometric = false
    }

    override fun doneAuthUserByBiometric() {
        _isUserAuthByBiometric = true
    }

    override fun resetAuthUserByPinCode() {
        _isUserAuthByPinCode = false
    }

    override fun doneAuthUserByPinCode() {
        _isUserAuthByPinCode = true
    }
}