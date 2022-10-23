package com.example.zeroapp.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentLoginBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.util.setToolbarIcon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseBindingFragment<FragmentLoginBinding>(FragmentLoginBinding::inflate) {

    private val viewModel by viewModels<LoginViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = this.inflater(inflater, container, false)

        setToolbarIcon(R.drawable.ic_back)

        binding!!.apply {
            setupBinding(this)
            return root
        }
    }

    private fun setupBinding(binding: FragmentLoginBinding) {

        binding.apply {
            buttonSignIn.setOnClickListener {
                val email = emailText.text.toString()
                val password = passwordText.text.toString()
                viewModel.onClickSignIn(email, password)
            }

            signUpHint.setOnClickListener {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
            }
        }

        viewModel.checkCurrentAuth()
        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            state?.let {
                when (it) {
                    is LoginState.EmptyFields -> showToast(getString(it.res))
                    is LoginState.ErrorFromFireBase -> showToast(it.message)
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