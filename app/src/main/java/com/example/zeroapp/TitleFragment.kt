package com.example.zeroapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.zeroapp.databinding.FragmentTitleBinding


class TitleFragment : Fragment() {

    private lateinit var bindind: FragmentTitleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindind = FragmentTitleBinding.inflate(inflater, container, false)

        val viewModel = ViewModelProvider(this)[TitleFragmentViewModel::class.java]

        return bindind.root
    }


}