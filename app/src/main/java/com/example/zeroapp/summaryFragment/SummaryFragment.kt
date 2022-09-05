package com.example.zeroapp.summaryFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.zeroapp.databinding.FragmentSummaryBinding

class SummaryFragment : Fragment() {

    companion object {
        fun newInstance() = SummaryFragment()
    }

    private lateinit var viewModel: SummaryViewModel
    private lateinit var bindind: FragmentSummaryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindind = FragmentSummaryBinding.inflate(inflater, container, false)
        return bindind.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[SummaryViewModel::class.java]
    }
}