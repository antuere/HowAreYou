package com.example.zeroapp.presentation.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentRegisterBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.util.setToolbarIcon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment :
    BaseBindingFragment<FragmentRegisterBinding>(FragmentRegisterBinding::inflate) {


    private val viewModel by viewModels<RegisterViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = this.inflater(inflater, container, false)
        setToolbarIcon(R.drawable.ic_back)

        binding!!.apply {
            setupBinding(this)
            return root
        }

    }


    private fun setupBinding(binding: FragmentRegisterBinding) {

        binding.apply {
            buttonSignUp.setOnClickListener {
                val name = nicknameText.text.toString()
                val email = emailText.text.toString()
                val password = passwordText.text.toString()
                val passwordConfirm = confirmPasswordText.text.toString()

                viewModel.onClickSignUp(email, password, passwordConfirm, name)
            }
        }

        viewModel.registerState.observe(viewLifecycleOwner) { state ->
            state?.let {
                when (it) {
                    is RegisterState.PasswordsError -> showToast(getString(it.res))
                    is RegisterState.EmptyFields -> showToast(getString(it.res))
                    is RegisterState.ErrorFromFireBase -> showToast(it.message)
                    else -> findNavController().navigateUp()
                }
                viewModel.stateReset()
            }

        }
    }

    private fun showToast(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}