package com.example.zeroapp.presentation.settingsFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.zeroapp.databinding.FragmentSettingsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment() {


    private val viewModel by viewModels<SettingsViewModel>()
    private lateinit var bindind: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindind = FragmentSettingsBinding.inflate(inflater, container, false)
        return bindind.root
    }

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