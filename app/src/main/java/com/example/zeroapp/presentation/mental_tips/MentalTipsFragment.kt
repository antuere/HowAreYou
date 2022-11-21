package com.example.zeroapp.presentation.mental_tips

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.zeroapp.databinding.FragmentMentalTipsBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MentalTipsFragment :
    BaseBindingFragment<FragmentMentalTipsBinding>(FragmentMentalTipsBinding::inflate) {

    private val viewModel by viewModels<MentalTipsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = this.inflater(inflater, container, false)
        return binding!!.root
    }

}