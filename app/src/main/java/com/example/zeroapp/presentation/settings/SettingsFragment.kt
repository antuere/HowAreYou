package com.example.zeroapp.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentSettingsBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.presentation.base.PrivacyManager
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.example.zeroapp.presentation.summary.BiometricAuthState
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment :
    BaseBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel by viewModels<SettingsViewModel>()

    @Inject
    lateinit var uiBiometricDialog: UIBiometricDialog

    @Inject
    lateinit var privacyManager: PrivacyManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.duration_normal).toLong()
        }

        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.duration_normal).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = this.inflater(inflater, container, false)
        viewModel.updateUserNickname()
        viewModel.checkCurrentUser()

        Timber.i("biometric auth we in createView")
        viewModel.userNickname.observe(viewLifecycleOwner) {
            it?.let {
                val welcomeText = "${getString(R.string.hello_user)} $it "
                binding!!.userNickname.text = welcomeText
            }
        }

        viewModel.isHasUser.observe(viewLifecycleOwner) {
            if (it) {
                changeUiWhenUserSignIn()
            }
        }

        viewModel.settings.observe(viewLifecycleOwner) {
            it?.let { settings ->
                when (settings.isBiometricEnabled) {
                    true -> {
                        binding!!.settingFingerPrintSwitch.isChecked = true
                    }
                    false -> binding!!.settingFingerPrintSwitch.isChecked = false
                }
                when (settings.isPinCodeEnabled) {
                    true -> binding!!.settingPinCodeSwitch.isChecked = true
                    false -> binding!!.settingPinCodeSwitch.isChecked = false
                }
            }
        }

        viewModel.biometricAuthState.observe(viewLifecycleOwner) {
            it?.let { state ->
                when (state) {
                    is BiometricAuthState.Successful -> {
                        binding!!.settingFingerPrintSwitch.isChecked = true
                        viewModel.saveSettings(
                            binding!!.settingFingerPrintSwitch.isChecked,
                            binding!!.settingPinCodeSwitch.isChecked
                        )
                        privacyManager.doneAuthUserByBiometric()
                    }
                    is BiometricAuthState.Error -> binding!!.settingFingerPrintSwitch.isChecked =
                        false
                }
            }
        }

        binding!!.settingFingerPrintSwitch.setOnCheckedChangeListener { _, isChecked ->
            Timber.i("fucking bug : biometric auth is change listener")
            when (isChecked) {
                true -> {
                    Timber.i("fucking bug : biometric auth is true")
                    setBiometricAuth()
                }
                false -> {
                    Timber.i("fucking bug : biometric auth is false")
                    resetBiometricAuth()
                }
            }
        }

        binding!!.settingPinCodeSwitch.setOnCheckedChangeListener { _, isChecked ->
            Timber.i("fucking bug : pincode auth is change listener")
            when (isChecked) {
                true -> setPinCodeAuth()
                false -> resetPinCodeAuth()
            }
        }

        binding!!.buttonSignIn.setOnClickListener {
            val transitionName = getString(R.string.transition_name_for_sign_in_methods)
            val extras = FragmentNavigatorExtras(binding!!.buttonSignIn to transitionName)
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToSignInMethodsFragment(),
                extras
            )
        }

        binding!!.buttonSignOut.setOnClickListener {
            viewModel.onSignOutClicked()

            val materialFade = MaterialFade().apply {
                duration = 350L
            }
            TransitionManager.beginDelayedTransition(container!!, materialFade)
            changeUiWhenUserSignOut()
        }

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun changeUiWhenUserSignIn() {
        binding!!.signInAdviceText.visibility = View.GONE

        binding!!.buttonSignIn.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToBottom = binding!!.howAreYouText.id
        }

        binding!!.buttonSignOut.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToBottom = binding!!.howAreYouText.id
        }

        binding!!.buttonSignIn.visibility = View.INVISIBLE
        binding!!.buttonSignOut.visibility = View.VISIBLE
    }

    private fun changeUiWhenUserSignOut() {
        binding!!.buttonSignIn.visibility = View.VISIBLE
        binding!!.signInAdviceText.visibility = View.VISIBLE
        binding!!.buttonSignOut.visibility = View.INVISIBLE

        binding!!.buttonSignIn.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToBottom = binding!!.signInAdviceText.id
        }

        binding!!.buttonSignOut.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToBottom = binding!!.signInAdviceText.id
        }
        binding!!.userNickname.text = getString(R.string.hello_user_plug)
    }

    private fun setBiometricAuth() {
        Timber.i("biometric auth is ${privacyManager.isUserAuthByBiometric}")
        if (!privacyManager.isUserAuthByBiometric) {
            uiBiometricDialog.startBiometricAuth(viewModel, this.requireActivity())
        }
    }

    private fun resetBiometricAuth() {
        privacyManager.resetAuthUserByBiometric()
        viewModel.nullifyBiometricAuthState()
        viewModel.saveSettings(
            binding!!.settingFingerPrintSwitch.isChecked,
            binding!!.settingPinCodeSwitch.isChecked
        )
    }

    private fun setPinCodeAuth() {
        if (!privacyManager.isUserAuthByPinCode) {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToPinCodeCreatingFragment())
        }
    }

    private fun resetPinCodeAuth() {
        privacyManager.resetAuthUserByPinCode()
        viewModel.saveSettings(
            binding!!.settingFingerPrintSwitch.isChecked,
            binding!!.settingPinCodeSwitch.isChecked
        )
    }
}