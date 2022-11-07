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
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.example.zeroapp.presentation.pin_code_сreating.PinCodeDialogFragment
import com.example.zeroapp.presentation.summary.BiometricAuthState
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment :
    BaseBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel by viewModels<SettingsViewModel>()

    @Inject
    lateinit var uiBiometricDialog: UIBiometricDialog


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
                binding!!.settingFingerPrintSwitch.isChecked = settings.isBiometricEnabled
                binding!!.settingPinCodeSwitch.isChecked = settings.isPinCodeEnabled
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
                        viewModel.privacyManager.doneAuthUserByBiometric()
                    }
                    is BiometricAuthState.Error -> binding!!.settingFingerPrintSwitch.isChecked =
                        false
                }
            }
        }

        viewModel.isPinCodeCreated.observe(viewLifecycleOwner) {
            it?.let { value ->
                binding!!.settingPinCodeSwitch.isChecked = value
            }
        }


        binding!!.settingFingerPrintSwitch.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> setBiometricAuth()
                false -> resetBiometricAuth()
            }
        }

        binding!!.settingPinCodeSwitch.setOnCheckedChangeListener { _, isChecked ->
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
        if (!viewModel.privacyManager.isUserAuthByBiometric) {
            uiBiometricDialog.startBiometricAuth(viewModel, this.requireActivity())
        }
    }

    private fun resetBiometricAuth() {
        viewModel.resetBiometricAuth()
        viewModel.saveSettings(
            binding!!.settingFingerPrintSwitch.isChecked,
            binding!!.settingPinCodeSwitch.isChecked
        )
    }

    private fun setPinCodeAuth() {
        if (!viewModel.privacyManager.isUserAuthByPinCode) {
            val pinCodeBottomSheet =
                PinCodeDialogFragment.newInstance(viewModel.pinCodeCreatingListener)

            pinCodeBottomSheet.show(
                requireActivity().supportFragmentManager,
                PinCodeDialogFragment.TAG
            )
        }
    }

    private fun resetPinCodeAuth() {
        viewModel.resetPinCodeAuth()
        viewModel.saveSettings(
            binding!!.settingFingerPrintSwitch.isChecked,
            false
        )
    }

}