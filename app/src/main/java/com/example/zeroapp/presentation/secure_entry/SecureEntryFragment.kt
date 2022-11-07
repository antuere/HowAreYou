package com.example.zeroapp.presentation.secure_entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentSecureEntryBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.presentation.pin_code_сreating.PinCodeCirclesState
import com.example.zeroapp.util.setToolbarIcon
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecureEntryFragment :
    BaseBindingFragment<FragmentSecureEntryBinding>(FragmentSecureEntryBinding::inflate) {

    private val viewModel by viewModels<SecureEntryViewModel>()


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
        numbersButtons.forEach { button ->
            button.setOnClickListener {
                val value = button.text.toString()
                viewModel.onClickNumber(value)
            }
        }

        viewModel.savedPinCode.observe(viewLifecycleOwner){
            it?.let { savedPin ->
                if (savedPin.length != 4) {
                    findNavController().navigate(SecureEntryFragmentDirections.actionSecureEntryFragmentToSummaryFragment())
                }
            }
        }

        viewModel.isCorrectPinCode.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigate(SecureEntryFragmentDirections.actionSecureEntryFragmentToSummaryFragment())
            } else {
                viewModel.resetEnteredPinCode()
                Toast.makeText(requireContext(), "Wrong pin-code", Toast.LENGTH_SHORT).show()
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

                    is PinCodeCirclesState.IsShowOne -> {
                        binding.entryCircle1.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowTwo -> {
                        binding.entryCircle2.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowThree -> {
                        binding.entryCircle3.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowFour -> {
                        binding.entryCircle4.setImageResource(R.drawable.ic_circle_filled)
                    }
                }
            }
        }
    }

}