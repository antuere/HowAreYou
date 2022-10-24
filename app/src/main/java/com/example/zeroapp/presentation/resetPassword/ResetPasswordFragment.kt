package com.example.zeroapp.presentation.resetPassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentResetPasswordBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.util.setToolbarIcon
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResetPasswordFragment :
    BaseBindingFragment<FragmentResetPasswordBinding>(FragmentResetPasswordBinding::inflate) {

    private val viewModel by viewModels<ResetPasswordViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = this.inflater(inflater, container, false)
        setToolbarIcon(R.drawable.ic_back)

        binding!!.apply {
            setupBinding(this)
            return root
        }

    }


    private fun setupBinding(binding: FragmentResetPasswordBinding) {

        binding.apply {
            buttonResetPassword.setOnClickListener {
                val email = emailForPasswordResetText.text.toString()

                viewModel.onClickResetPassword(email)
            }
        }

        viewModel.resetState.observe(viewLifecycleOwner) { state ->
            state?.let {
                when (it) {
                    is ResetPasswordState.Successful -> {
                        findNavController().navigateUp()
                        showToast(getString(it.res))
                    }
                    is ResetPasswordState.EmptyFields -> showToast(getString(it.res))
                    is ResetPasswordState.ErrorFromFireBase -> showToast(it.message)
                }
                viewModel.navigationDone()
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG
        ).show()
    }
}