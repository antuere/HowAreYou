package com.example.zeroapp.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentSettingsBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.google.android.material.transition.MaterialElevationScale
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
                val welcomeText = " ${getString(R.string.hello_user)} $it "
                binding!!.welcomeUser.text = welcomeText
            }
        }

        binding!!.buttonSignIn.setOnClickListener {
            findNavController().navigate(SettingsFragmentDirections.actionSettingsFragmentToLoginFragment())

        }

        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = 250L
        }
        super.onViewCreated(view, savedInstanceState)
    }
}