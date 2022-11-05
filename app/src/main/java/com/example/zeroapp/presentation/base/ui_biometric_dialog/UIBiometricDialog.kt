package com.example.zeroapp.presentation.base.ui_biometric_dialog

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import com.example.zeroapp.R
import java.util.concurrent.Executor


class UIBiometricDialog(private val context: Context) {

    private val biometricManager: BiometricManager by lazy {
        BiometricManager.from(context)
    }

    private val executor: Executor by lazy {
        ContextCompat.getMainExecutor(context)
    }
    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: BiometricPrompt.PromptInfo? = null

    private var _isUserAuth = false
    val isUserAuth: Boolean
        get() = _isUserAuth


    private fun checkDeviceHasBiometric(): Boolean {
        val result =
            when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
                BiometricManager.BIOMETRIC_SUCCESS -> true
                else -> false
            }
        return result
    }

    fun startBiometricAuth(biometricListener: IUIBiometricListener, activity: FragmentActivity) {
        if (checkDeviceHasBiometric()) {
            biometricPrompt = BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        biometricListener.onBiometricAuthFailed()
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        biometricListener.onBiometricAuthSuccess()
                        _isUserAuth = true
                    }
                })

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(context.getString(R.string.biometric_auth_title))
                .setDescription(context.getString(R.string.biometric_auth_desc))
                .setNegativeButtonText(context.getString(R.string.biometric_auth_negative_btn))
                .build()

            biometricPrompt!!.authenticate(promptInfo!!)
        }
    }

    fun resetAuthUser() {
        _isUserAuth = false
    }
}