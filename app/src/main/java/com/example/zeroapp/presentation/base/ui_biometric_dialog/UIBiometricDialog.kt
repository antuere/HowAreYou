package com.example.zeroapp.presentation.base.ui_biometric_dialog

import android.app.KeyguardManager
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

    private val keyguardManager: KeyguardManager by lazy {
        context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
    }

    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: BiometricPrompt.PromptInfo? = null

    val deviceHasBiometricHardware: BiometricsAvailableState
        get() {
            return when (biometricManager.canAuthenticate(BIOMETRIC_WEAK or DEVICE_CREDENTIAL)) {
                BiometricManager.BIOMETRIC_SUCCESS, BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    BiometricsAvailableState.Available
                }
                else -> BiometricsAvailableState.NoHardware
            }
        }


    private fun checkIsEnrolledBiometric(): BiometricsAvailableState {
        val result =
            when (biometricManager.canAuthenticate(BIOMETRIC_WEAK or DEVICE_CREDENTIAL)) {
                BiometricManager.BIOMETRIC_SUCCESS -> {
                    BiometricsAvailableState.Available
                }
                BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                    BiometricsAvailableState.NoHardware
                }
                BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                    BiometricsAvailableState.NoneEnrolled
                }
                else -> BiometricsAvailableState.SomeError(R.string.biometric_unknown_error)

            }
        return result
    }


    fun startBiometricAuth(
        biometricListener: IUIBiometricListener,
        activity: FragmentActivity
    ) {
        val availableState = checkIsEnrolledBiometric()
        if (availableState is BiometricsAvailableState.NoneEnrolled) {
            biometricListener.noneEnrolled()
        }

        if (availableState is BiometricsAvailableState.Available) {
            biometricPrompt = BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(
                        errorCode: Int,
                        errString: CharSequence
                    ) {
                        super.onAuthenticationError(errorCode, errString)
                        biometricListener.onBiometricAuthFailed()
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        biometricListener.onBiometricAuthSuccess()
                    }

                })

            promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle(context.getString(R.string.biometric_auth_title))
                .setDescription(context.getString(R.string.biometric_auth_desc))
                .setNegativeButtonText(context.getString(R.string.biometric_auth_negative_btn))
                .setConfirmationRequired(false)
//                .setAllowedAuthenticators(BIOMETRIC_WEAK or DEVICE_CREDENTIAL)
                .build()

            if (keyguardManager.isDeviceSecure) {
                biometricPrompt!!.authenticate(promptInfo!!)
            }
        }
    }
}