package com.example.zeroapp.presentation.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.zeroapp.databinding.FragmentSettingsBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : BaseBindingFragment<FragmentSettingsBinding>(FragmentSettingsBinding::inflate) {


    private val viewModel by viewModels<SettingsViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

//        postponeEnterTransition()
//        view.doOnPreDraw {
//            startPostponedEnterTransition()
//        }
//
//        reenterTransition = MaterialElevationScale(true).apply {
//            duration = 250L
//        }
        super.onViewCreated(view, savedInstanceState)
    }
}