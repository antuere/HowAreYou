package com.example.zeroapp.presentation.summary

import android.os.Bundle
import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.*
import com.example.zeroapp.databinding.FragmentSummaryBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SummaryFragment :
    BaseBindingFragment<FragmentSummaryBinding>(FragmentSummaryBinding::inflate) {

    private val viewModel by viewModels<SummaryViewModel>()
    private lateinit var fabButton: FloatingActionButton


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateInfo()

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.duration_normal).toLong()
        }

        fabButton = binding!!.fabAddButton
        fabButton.setOnClickListener {

            var transitionName = getString(R.string.transition_name_for_sum)
            if (it.tag == getString(R.string.add)) {
                val extrasAdd = FragmentNavigatorExtras(binding!!.fabAddButton to transitionName)
                findNavController().navigate(
                    SummaryFragmentDirections.actionSummaryFragmentToAddDayFragment(), extrasAdd
                )
            } else {
                transitionName = getString(R.string.transition_name)
                val extrasSmile = FragmentNavigatorExtras(binding!!.fabAddButton to transitionName)
                findNavController().navigate(
                    SummaryFragmentDirections.actionSummaryFragmentToDetailFragment(
                        viewModel.lastDay.value!!.dayId
                    ), extrasSmile
                )
            }
        }

        viewModel.wishText.observe(viewLifecycleOwner) {
            it?.let { wishString ->
                binding!!.wishText.text = wishString
            }
        }


        binding!!.favorites.setOnClickListener {
            val transitionName = getString(R.string.transition_name_for_fav)
            val extras = FragmentNavigatorExtras(binding!!.favorites to transitionName)
            findNavController().navigate(
                SummaryFragmentDirections.actionSummaryFragmentToFavoritesFragment(), extras
            )
        }

        binding!!.cats.setOnClickListener {
            val transitionName = getString(R.string.transition_name_for_cats)
            val extras = FragmentNavigatorExtras(binding!!.cats to transitionName)
            findNavController().navigate(
                SummaryFragmentDirections.actionSummaryFragmentToCatsFragment(), extras
            )
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.hideAddButton.observe(viewLifecycleOwner) {
            fabButton.tag = getString(it.tag)
            fabButton.setImageResource(it.image)
            if (it.tag == R.string.smile) {
                binding!!.fabAddButton.transitionName = getString(it.transitionName)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.hideAddButton.observe(viewLifecycleOwner) {
            if (it.tag == R.string.add) {
                binding!!.fabAddButton.transitionName = getString(it.transitionName)
            }
        }
    }
}