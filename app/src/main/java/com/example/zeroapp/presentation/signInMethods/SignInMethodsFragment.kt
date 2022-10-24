package com.example.zeroapp.presentation.signInMethods

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentSignInMethodsBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.util.createSharedElementEnterTransition
import com.example.zeroapp.util.setToolbarIcon
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInMethodsFragment :
    BaseBindingFragment<FragmentSignInMethodsBinding>(FragmentSignInMethodsBinding::inflate) {

    private val viewModel by viewModels<SignInMethodsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = createSharedElementEnterTransition()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = this.inflater(inflater, container, false)

        setToolbarIcon(R.drawable.ic_back)

        binding!!.buttonEmailMethod.setOnClickListener {
            val transitionName = getString(R.string.transition_name_for_sign_in)
            val extras = FragmentNavigatorExtras(binding!!.buttonEmailMethod to transitionName)
            findNavController().navigate(
                SignInMethodsFragmentDirections.actionSignInMethodsFragmentToLoginFragment(),
                extras
            )
        }


        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        viewModel.checkCurrentAuth()
        viewModel.isHasUser.observe(viewLifecycleOwner) {
            if (it) {
                findNavController().navigateUp()
                viewModel.navigationDone()
            }
        }

    }


}