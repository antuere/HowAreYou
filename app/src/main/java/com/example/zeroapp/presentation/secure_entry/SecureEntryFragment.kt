package com.example.zeroapp.presentation.secure_entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentSecureEntryBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.presentation.base.ui_biometric_dialog.UIBiometricDialog
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener
import com.example.zeroapp.presentation.pin_code_сreating.PinCodeCirclesState
import com.example.zeroapp.presentation.summary.BiometricAuthState
import com.example.zeroapp.util.setToolbarIcon
import com.example.zeroapp.util.startOnClickAnimation
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SecureEntryFragment :
    BaseBindingFragment<FragmentSecureEntryBinding>(FragmentSecureEntryBinding::inflate) {

    private val viewModel by viewModels<SecureEntryViewModel>()

    @Inject
    lateinit var uiBiometricDialog: UIBiometricDialog

    private val dialogListener: UIDialogListener by lazy {
        UIDialogListener(requireContext(), viewModel)
    }

    private val numbersButtons: List<Button> by lazy {
        listOf(
            binding!!.entryBtnNumber0,
            binding!!.entryBtnNumber1,
            binding!!.entryBtnNumber2,
            binding!!.entryBtnNumber3,
            binding!!.entryBtnNumber4,
            binding!!.entryBtnNumber5,
            binding!!.entryBtnNumber6,
            binding!!.entryBtnNumber7,
            binding!!.entryBtnNumber8,
            binding!!.entryBtnNumber9
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setToolbarIcon(R.drawable.ic_back)

        binding = this.inflater(inflater, container, false)

        binding!!.apply {
            setupBinding(this)
            return root
        }
    }

    private fun setupBinding(binding: FragmentSecureEntryBinding) {
        dialogListener.collect(this)

        numbersButtons.forEach { button ->
            button.setOnClickListener {
                val value = button.text.toString()
                viewModel.onClickNumber(value)
            }
        }

        binding.entryClearPin.setOnClickListener {
            startOnClickAnimation(it)
            viewModel.resetEnteredPinCode()
        }

        binding.biomAuthImage.setOnClickListener {
            startOnClickAnimation(it)
            viewModel.onClickBiometricBtn()
        }

        binding.entryExitFromAccText.setOnClickListener {
            startOnClickAnimation(it)
            viewModel.onClickSignOut()
        }


        viewModel.isNavigateToHomeFragment.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(
                    SecureEntryFragmentDirections.actionSecureEntryFragmentToSummaryFragment()
                )
                viewModel.doneNavigateToHomeFragment()
            }
        }

        viewModel.biometricAuthState.observe(viewLifecycleOwner) {
            it?.let { state ->
                when (state) {
                    is BiometricAuthState.Successful -> viewModel.navigateToHomeFragment()
                    is BiometricAuthState.Error -> viewModel.biomAuthDialogCanceled()
                }
            }
        }

        viewModel.settings.observe(viewLifecycleOwner) {
            it?.let { settings ->
                if (!settings.isPinCodeEnabled && !settings.isBiometricEnabled) {
                    viewModel.navigateToHomeFragment()
                }
                if (settings.isBiometricEnabled) {
                    viewModel.showBiometricAuth(withDelay = true)
                    viewModel.resetIsShowBiometricAuth()
                }
            }
        }

        viewModel.isShowBiometricAuth.observe(viewLifecycleOwner) {
            if (it) {
                uiBiometricDialog.startBiometricAuth(
                    viewModel.biometricAuthStateListener,
                    this.requireActivity()
                )
            }
        }

        viewModel.isCorrectPinCode.observe(viewLifecycleOwner) {
            if (it) {
                viewModel.navigateToHomeFragment()
            } else {
                showSnackBar(
                    stringResId = R.string.wrong_pin_code,
                    anchorView = binding.entryDivider
                )
                viewModel.resetEnteredPinCode()
            }
        }

        viewModel.userPinCode.observe(viewLifecycleOwner) {
            it?.let { pinCode ->
                if (pinCode.length == 4) {
                    viewModel.validateEnteredPinCode(pinCode)
                }
            }
        }

        viewModel.pinCodeCirclesState.observe(viewLifecycleOwner) {
            it?.let { state ->
                when (state) {
                    is PinCodeCirclesState.IsShowNone -> {
                        binding.entryCircle1.setImageResource(R.drawable.ic_outline_outlined)
                        binding.entryCircle2.setImageResource(R.drawable.ic_outline_outlined)
                        binding.entryCircle3.setImageResource(R.drawable.ic_outline_outlined)
                        binding.entryCircle4.setImageResource(R.drawable.ic_outline_outlined)
                    }
                    is PinCodeCirclesState.IsShowFirst -> {
                        binding.entryCircle1.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowSecond -> {
                        binding.entryCircle2.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowThird -> {
                        binding.entryCircle3.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowFourth -> {
                        binding.entryCircle4.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowAll -> {
                        binding.entryCircle1.setImageResource(R.drawable.ic_circle_filled)
                        binding.entryCircle2.setImageResource(R.drawable.ic_circle_filled)
                        binding.entryCircle3.setImageResource(R.drawable.ic_circle_filled)
                        binding.entryCircle4.setImageResource(R.drawable.ic_circle_filled)
                    }
                }
            }
        }
    }

}