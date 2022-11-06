package com.example.zeroapp.presentation.base

class PrivacyManager {

    private var _isUserAuthByBiometric = false
    val isUserAuthByBiometric: Boolean
        get() = _isUserAuthByBiometric

    private var _isUserAuthByPinCode = false
    val isUserAuthByPinCode: Boolean
        get() = _isUserAuthByPinCode

    fun resetAuthUserByBiometric() {
        _isUserAuthByBiometric = false
    }

    fun doneAuthUserByBiometric() {
        _isUserAuthByBiometric = true
    }

    fun resetAuthUserByPinCode() {
        _isUserAuthByPinCode = false
    }

    fun doneAuthUserByPinCode() {
        _isUserAuthByPinCode = true
    }

    fun doneAuthUser() {
        _isUserAuthByPinCode = true
        _isUserAuthByBiometric = true
    }

}