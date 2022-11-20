package com.example.zeroapp.presentation.settings

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.biometric.BiometricManager
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.updateLayoutParams
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentSettingsBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricsAvailableState
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.example.zeroapp.presentation.pin_code_сreating.PinCodeDialogFragment
import com.example.zeroapp.presentation.base.ui_biometric_dialog.BiometricAuthState
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment :
    BaseBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel by viewModels<SettingsViewModel>()


    private val dialogListener: UIDialogListener by lazy {
        UIDialogListener(requireContext(), viewModel)
    }

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
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        Timber.i("navigate upd : createView setting fragment")
        binding = this.inflater(inflater, container, false)
        dialogListener.collect(this, withNeutralBtn = true)

        binding!!.settingWorriedDialogSwitch.setOnCheckedChangeListener { _, isChecked ->
            viewModel.saveShowWorriedDialog(isChecked)
        }

        binding!!.settingBiomAuthSwitch.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> viewModel.setBiometricAuth()
                false -> {
                    viewModel.resetBiometricAuthAndSaveSettings(
                        binding!!.settingBiomAuthSwitch.isChecked,
                        binding!!.settingPinCodeSwitch.isChecked
                    )
                }
            }
        }

        binding!!.settingPinCodeSwitch.setOnCheckedChangeListener { _, isChecked ->
            when (isChecked) {
                true -> {
                    viewModel.setPinCodeAuth()
                }
                false -> {
                    binding!!.settingBiomAuthSwitch.isChecked = false
                    viewModel.resetPinCodeAuth()
                    viewModel.resetBiometricAuthAndSaveSettings(isUseBiometric = false, isUsePinCode = false)
                    hideBiometricsSetting(container!!)
                }
            }
        }

        binding!!.buttonSignIn.setOnClickListener {
            val transitionName = getString(R.string.transition_name_for_sign_in_methods)
            val extras = FragmentNavigatorExtras(binding!!.buttonSignIn to transitionName)
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToSignInMethodsFragment(), extras
            )
        }

        binding!!.buttonSignOut.setOnClickListener {
            viewModel.onClickSignOut()
        }

        viewModel.userNickname.observe(viewLifecycleOwner) {
            it?.let { userName ->
                if(userName != "UserUnknownError175"){
                    val welcomeText = "${getString(R.string.hello_user)} $userName"
                    binding!!.userNickname.text = welcomeText

                    showSignOutButton()
                } else {
                    showSignInButton(container!!)
                }
            }
        }

        viewModel.settings.observe(viewLifecycleOwner) {
            it?.let { settings ->
                binding!!.settingBiomAuthSwitch.isChecked = settings.isBiometricEnabled
                binding!!.settingWorriedDialogSwitch.isChecked = settings.isShowWorriedDialog

                binding!!.settingPinCodeSwitch.isChecked = settings.isPinCodeEnabled

                if (settings.isPinCodeEnabled && viewModel.biometricAvailableState.value is BiometricsAvailableState.Available) {
                    showBiometricSetting(container!!)
                }
            }
        }

        viewModel.biometricAvailableState.observe(viewLifecycleOwner) {
            it?.let { state ->
                when (state) {
                    is BiometricsAvailableState.NoneEnrolled -> {
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                            val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                putExtra(
                                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                    BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
                                )
                            }
                            startActivity(enrollIntent)
                        } else {
                            showSnackBar(stringResId = R.string.biometric_none_enroll)
                            binding!!.settingBiomAuthSwitch.isChecked = false
                        }

                    }
                    is BiometricsAvailableState.NoHardware -> hideBiometricsSetting(container!!)
                    is BiometricsAvailableState.SomeError -> {
                        showSnackBar(stringResId = R.string.biometric_unknown_error)
                        binding!!.settingBiomAuthSwitch.isChecked = false
                    }
                    is BiometricsAvailableState.Available -> {}
                }
            }
        }

        viewModel.biometricAuthState.observe(viewLifecycleOwner) {
            it?.let { state ->
                when (state) {
                    BiometricAuthState.SUCCESS -> {
                        binding!!.settingBiomAuthSwitch.isChecked = true
                        viewModel.saveSettings(
                            binding!!.settingBiomAuthSwitch.isChecked,
                            binding!!.settingPinCodeSwitch.isChecked
                        )
                        viewModel.nullifyBiometricAuthState()
                        showSnackBar(stringResId = R.string.biom_auth_create_success)
                    }
                    BiometricAuthState.ERROR -> binding!!.settingBiomAuthSwitch.isChecked = false
                }
            }
        }

        viewModel.isPinCodeCreated.observe(viewLifecycleOwner) {
            it?.let { value ->
                binding!!.settingPinCodeSwitch.isChecked = value
                if (value) {
                    if (viewModel.biometricAvailableState.value is BiometricsAvailableState.Available) {
                        showBiometricSetting(container!!)
                    }
                    showSnackBar(stringResId = R.string.pin_code_create_success)
                    viewModel.nullifyIsPinCodeCreated()
                }
            }
        }

        viewModel.isStartSetBiometric.observe(viewLifecycleOwner) {
            if (it) {
                uiBiometricDialog.startBiometricAuth(
                    viewModel.biometricAuthStateListener, this.requireActivity()
                )
                viewModel.resetIsStartSetBiometric()
            }
        }

        viewModel.isStartSetPinCode.observe(viewLifecycleOwner) {
            if (it) {
                val pinCodeBottomSheet =
                    PinCodeDialogFragment.newInstance(viewModel.pinCodeCreatingListener)
                pinCodeBottomSheet.show(
                    requireActivity().supportFragmentManager, PinCodeDialogFragment.TAG
                )

                viewModel.resetIsStartSetPinCode()
            }
        }

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }

    private fun showSignOutButton() {
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

    private fun showSignInButton(viewGroup: ViewGroup) {
        val materialFade = MaterialFade().apply {
            duration = 250L
        }
        TransitionManager.beginDelayedTransition(viewGroup, materialFade)

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

    private fun showBiometricSetting(viewGroup: ViewGroup, withAnimation: Boolean = true) {
        if (withAnimation) {
            val materialFade = MaterialFade().apply {
                duration = 250L
            }
            TransitionManager.beginDelayedTransition(viewGroup, materialFade)
        }

        binding!!.settingBiomAuthSwitch.visibility = View.VISIBLE
        binding!!.settingBiomAuthTitle.visibility = View.VISIBLE
        binding!!.settingBiomAuthDesc.visibility = View.VISIBLE

        binding!!.materialDividerUnderPrivacy.updateLayoutParams<ConstraintLayout.LayoutParams> {
            topToBottom = binding!!.settingBiomAuthSwitch.id
        }
    }

    private fun hideBiometricsSetting(viewGroup: ViewGroup) {
        val materialFade = MaterialFade().apply {
            duration = 250L
        }
        TransitionManager.beginDelayedTransition(viewGroup, materialFade)

        binding!!.settingBiomAuthSwitch.visibility = View.INVISIBLE
        binding!!.settingBiomAuthTitle.visibility = View.INVISIBLE
        binding!!.settingBiomAuthDesc.visibility = View.INVISIBLE

        lifecycleScope.launch {
            delay(250L)
            binding!!.materialDividerUnderPrivacy.updateLayoutParams<ConstraintLayout.LayoutParams> {
                topToBottom = binding!!.settingPinCodeSwitch.id
            }
        }

    }
}