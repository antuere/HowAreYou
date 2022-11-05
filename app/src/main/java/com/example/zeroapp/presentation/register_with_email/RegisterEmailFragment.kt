package com.example.zeroapp.presentation.register_with_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentRegisterEmailBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.util.setToolbarIcon
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterEmailFragment :
    BaseBindingFragment<FragmentRegisterEmailBinding>(FragmentRegisterEmailBinding::inflate) {


    private val viewModel by viewModels<RegisterEmailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)
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


    private fun setupBinding(binding: FragmentRegisterEmailBinding) {

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
                    is RegisterState.Successful -> findNavController().navigateUp()
                    is RegisterState.EmptyFields -> showToast(getString(it.res))
                    is RegisterState.PasswordsError -> showToast(getString(it.res))
                    is RegisterState.ErrorFromFireBase -> showToast(it.message)
                }
                viewModel.nullifyState()
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