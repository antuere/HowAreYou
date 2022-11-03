package com.example.zeroapp.util

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.*
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import java.util.concurrent.Executor


class MyBiometricManager(val context: Context, val fragment: Fragment) {

    private val biometricManager: BiometricManager by lazy {
        BiometricManager.from(context)
    }

    private val executor: Executor by lazy {
        ContextCompat.getMainExecutor(context)
    }

    private var biometricPrompt: BiometricPrompt? = null
    private var promptInfo: BiometricPrompt.PromptInfo? = null


    private fun checkDeviceHasBiometric(): Boolean {
        val result =
            when (biometricManager.canAuthenticate(BIOMETRIC_STRONG or DEVICE_CREDENTIAL)) {
                BiometricManager.BIOMETRIC_SUCCESS -> true
                else -> false
            }
        Timber.i("auth error : result is $result")
        return result
    }

    fun setAuth() {
        if (checkDeviceHasBiometric()) {
            Timber.i("auth error : we in builder")
            biometricPrompt = BiometricPrompt(
                fragment,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        fragment.findNavController().navigateUp()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        fragment.findNavController().navigateUp()
                    }
                })

            promptInfo = BiometricPrompt.PromptInfo.Builder().build()

            biometricPrompt!!.authenticate(promptInfo!!)
        }
    }


}