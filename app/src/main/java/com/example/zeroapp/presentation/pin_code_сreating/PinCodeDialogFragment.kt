package com.example.zeroapp.presentation.pin_code_сreating

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.viewModels
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentPinCodeCreatingBinding
import com.example.zeroapp.util.startOnClickAnimation
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PinCodeDialogFragment : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "PinCodeBottomSheet"

        fun newInstance(iPinCodeCreatingListener: IPinCodeCreatingListener): PinCodeDialogFragment {
            val result = PinCodeDialogFragment()
            result.setPinCodeCreatingListener(iPinCodeCreatingListener)
            return result
        }
    }

    private var pinCodeCreatingListener: IPinCodeCreatingListener? = null
    private var isCanceled = false

    private val viewModel by viewModels<PinCodeDialogViewModel>()

    private var binding: FragmentPinCodeCreatingBinding? = null

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

    private fun setPinCodeCreatingListener(value: IPinCodeCreatingListener) {
        pinCodeCreatingListener = value
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPinCodeCreatingBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        numbersButtons.forEach { button ->
            button.setOnClickListener {
                val value = button.text.toString()
                viewModel.onClickNumber(value)
            }
        }

        binding!!.createClearPin.setOnClickListener {
            startOnClickAnimation(it)
            viewModel.resetEnteredPinCode()
        }

        viewModel.isNavigateUp.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    this.dismiss()
                    viewModel.doneNavigationUp()
                }
            }
        }

        viewModel.userPinCode.observe(viewLifecycleOwner) {
            it?.let { pinCode ->
                if (pinCode.length == 4) {
                    viewModel.pinCodeCreated()
                }
            }
        }

        viewModel.pinCodeCirclesState.observe(viewLifecycleOwner) {
            it?.let { state ->
                when (state) {
                    is PinCodeCirclesState.IsShowFirst -> {
                        binding!!.circle1.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowSecond -> {
                        binding!!.circle2.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowThird -> {
                        binding!!.circle3.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowFourth -> {
                        binding!!.circle4.setImageResource(R.drawable.ic_circle_filled)
                    }
                    is PinCodeCirclesState.IsShowNone -> {
                        binding!!.circle1.setImageResource(R.drawable.ic_outline_outlined)
                        binding!!.circle2.setImageResource(R.drawable.ic_outline_outlined)
                        binding!!.circle3.setImageResource(R.drawable.ic_outline_outlined)
                        binding!!.circle4.setImageResource(R.drawable.ic_outline_outlined)
                    }
                    is PinCodeCirclesState.IsShowAll -> {}
                }

            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        super.onCancel(dialog)
        isCanceled = true
        pinCodeCreatingListener!!.pinCodeNotCreated()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (!isCanceled) pinCodeCreatingListener!!.pinCodeCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}