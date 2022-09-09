package com.example.zeroapp.settingsFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import com.example.zeroapp.databinding.FragmentSettingsBinding
import com.google.android.material.transition.MaterialElevationScale

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel
    private lateinit var bindind: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindind = FragmentSettingsBinding.inflate(inflater, container, false)
        return bindind.root
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
        viewModel = ViewModelProvider(this)[SettingsViewModel::class.java]
    }
}