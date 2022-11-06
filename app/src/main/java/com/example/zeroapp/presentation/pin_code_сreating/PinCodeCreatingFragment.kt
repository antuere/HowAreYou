package com.example.zeroapp.presentation.pin_code_сreating

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentPinCodeCreatingBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.presentation.base.PrivacyManager
import com.example.zeroapp.util.setToolbarIcon
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PinCodeCreatingFragment :
    BaseBindingFragment<FragmentPinCodeCreatingBinding>(FragmentPinCodeCreatingBinding::inflate) {

    private val viewModel by viewModels<PinCodeCreatingViewModel>()

    @Inject
    lateinit var privacyManager: PrivacyManager

    private val numbersButtons: List<Button> by lazy {
        listOf(
            binding!!.btnNumber0,
            binding!!.btnNumber1,
            binding!!.btnNumber2,
            binding!!.btnNumber3,
            binding!!.btnNumber4,
            binding!!.btnNumber5,
            binding!!.btnNumber6,
            binding!!.btnNumber7,
            binding!!.btnNumber8,
            binding!!.btnNumber9
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setToolbarIcon(R.drawable.ic_back)

        binding = this.inflater(inflater, container, false)

        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        numbersButtons.forEach { button ->
            button.setOnClickListener {
                val value = button.text.toString()
                viewModel.onClickNumber(value)
            }
        }

        viewModel.isNavigateUp.observe(viewLifecycleOwner) {
            it?.let {
                if(it) {
                    findNavController().navigateUp()
                    viewModel.doneNavigationUp()
                }
            }
        }

        viewModel.userPassword.observe(viewLifecycleOwner) {
            it?.let { password ->
                if (password.length == 4) {
                    privacyManager.doneAuthUserByPinCode()
                    viewModel.saveSettings()
                }
            }
        }

        viewModel.pinCodeCirclesState.observe(viewLifecycleOwner) {
            it?.let { state ->
                when (state) {
                    is PinCodeCirclesState.IsShowOne -> {
                        binding!!.circle1.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowTwo -> {
                        binding!!.circle2.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowThree -> {
                        binding!!.circle3.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowFour -> {
                        binding!!.circle4.setImageResource(R.drawable.ic_circle_filled)
                    }
                }

            }
        }

    }

}