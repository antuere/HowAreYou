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
import com.example.zeroapp.util.startOnClickAnimation
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class LoginEmailFragment :
    BaseBindingFragment<FragmentLoginEmailBinding>(FragmentLoginEmailBinding::inflate) {

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
        binding = this.inflater(inflater, container, false)
        binding!!.apply {
            setupBinding(this)
            return root
        }
    }

    private fun setupBinding(binding: FragmentLoginEmailBinding) {
        Timber.i("navigate upd : createView login email")

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

        viewModel.isShowLoginProgressIndicator.observe(viewLifecycleOwner) {
            if (it) {
                showProgressIndicator()
            } else {
                hideProgressIndicator()
            }
        }

        viewModel.loginState.observe(viewLifecycleOwner) { state ->
            state?.let {
                when (it) {
                    is LoginState.Successful -> {
                        findNavController().popBackStack(R.id.signInMethodsFragment, true)
                        viewModel.resetIsShowLoginProgressIndicator(withDelay = true)
                    }
                    is LoginState.EmptyFields -> showSnackBar(stringResId = it.res)
                    is LoginState.ErrorFromFireBase -> {
                        showSnackBarByString(string = it.message)
                        viewModel.resetIsShowLoginProgressIndicator()
                    }
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

    private fun showProgressIndicator() {
        binding!!.apply {
            if (stubProgressLogin.root.parent != null) {
                stubProgressLogin.root.inflate()
            } else {
                stubProgressLogin.root.visibility = View.VISIBLE
            }
            emailLayout.visibility = View.INVISIBLE
            passwordLayout.visibility = View.INVISIBLE
            resetPasswordHint.visibility = View.INVISIBLE
            signUpHint.visibility = View.INVISIBLE
            buttonSignIn.visibility = View.INVISIBLE
        }

    }

    private fun hideProgressIndicator() {
        binding!!.apply {
            stubProgressLogin.root.visibility = View.GONE
            emailLayout.visibility = View.VISIBLE
            passwordLayout.visibility = View.VISIBLE
            resetPasswordHint.visibility = View.VISIBLE
            signUpHint.visibility = View.VISIBLE
            buttonSignIn.visibility = View.VISIBLE
        }
    }

}