package com.example.zeroapp.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentSettingsBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment :
    BaseBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {

    private val viewModel by viewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = this.inflater(inflater, container, false)

        viewModel.updateUserNickname()

        viewModel.userNickname.observe(viewLifecycleOwner) {
            it?.let {
                binding!!.buttonSignIn.visibility = View.INVISIBLE
                binding!!.buttonSignOut.visibility = View.VISIBLE
                val welcomeText = " ${getString(R.string.hello_user)} $it "
                binding!!.welcomeUser.text = welcomeText
            }
        }

        binding!!.buttonSignIn.setOnClickListener {
            val transitionName = getString(R.string.transition_name_for_sign_in_methods)
            val extras = FragmentNavigatorExtras(binding!!.buttonSignIn to transitionName)
            findNavController().navigate(
                SettingsFragmentDirections.actionSettingsFragmentToSignInMethodsFragment(),
                extras
            )

        }

        binding!!.buttonSignOut.setOnClickListener {
            viewModel.onSignOutClicked()

            val materialFade = MaterialFade().apply {
                duration = 150L
            }
            TransitionManager.beginDelayedTransition(container!!, materialFade)

            binding!!.buttonSignOut.visibility = View.INVISIBLE
            binding!!.buttonSignIn.visibility = View.VISIBLE
            binding!!.welcomeUser.text = ""

        }

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        enterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.duration_normal).toLong()
        }

    }
}