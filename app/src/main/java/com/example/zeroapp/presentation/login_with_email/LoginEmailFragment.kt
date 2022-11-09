package com.example.zeroapp.presentation.login_with_email

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentLoginEmailBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.util.createSharedElementEnterTransition
import com.example.zeroapp.util.setToolbarIcon
import com.example.zeroapp.util.startOnClickAnimation
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginEmailFragment : BaseBindingFragment<FragmentLoginEmailBinding>(FragmentLoginEmailBinding::inflate) {

    private val viewModel by viewModels<LoginEmailViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = createSharedElementEnterTransition()

        exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
        reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        setToolbarIcon(R.drawable.ic_back)

        binding = this.inflater(inflater, container, false)

        binding!!.apply {
            setupBinding(this)
            return root
        }
    }

    private fun setupBinding(binding: FragmentLoginEmailBinding) {

        viewModel.checkCurrentAuth()

        binding.apply {
            buttonSignIn.setOnClickListener {
                val email = emailText.text.toString()
                val password = passwordText.text.toString()
                viewModel.onClickSignIn(email, password)
            }

            signUpHint.setOnClickListener {
                startOnClickAnimation(it)
                findNavController().navigate(LoginEmailFragmentDirections.actionLoginFragmentToRegisterFragment())
            }

            resetPasswordHint.setOnClickListener {
                startOnClickAnimation(it)
                findNavController().navigate(LoginEmailFragmentDirections.actionLoginFragmentToResetPasswordFragment())
            }
        }

        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            state?.let {
                when (it) {
                    is LoginState.Successful -> findNavController().navigateUp()
                    is LoginState.EmptyFields -> showSnackBar(stringResId = it.res)
                    is LoginState.ErrorFromFireBase -> showSnackBarByString(string = it.message)
                }
                viewModel.nullifyState()
            }
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
    }
}