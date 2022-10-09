package com.example.zeroapp.presentation.summary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.*
import com.example.zeroapp.databinding.FragmentSummaryBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class SummaryFragment : Fragment() {

    private val viewModel by viewModels<SummaryViewModel>()
    private lateinit var bindind: FragmentSummaryBinding
    private lateinit var fabButton: FloatingActionButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindind = FragmentSummaryBinding.inflate(inflater, container, false)
        Timber.i("sum log: onCreateView")


        return bindind.root
    }

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

        fabButton = bindind.fabAddButton
        fabButton.setOnClickListener {

            var transitionName = getString(R.string.transition_name_for_sum)
            if (it.tag == getString(R.string.add)) {
                val extrasAdd = FragmentNavigatorExtras(bindind.coordinator to transitionName)
                findNavController().navigate(
                    SummaryFragmentDirections.actionSummaryFragmentToAddDayFragment(), extrasAdd
                )
            } else {
                transitionName = getString(R.string.transition_name)
                val extrasSmile = FragmentNavigatorExtras(bindind.coordinator to transitionName)
                findNavController().navigate(
                    SummaryFragmentDirections.actionSummaryFragmentToDetailFragment(
                        viewModel.lastDay.value!!.dayId
                    ), extrasSmile
                )
            }
        }

    }



    override fun onStart() {
        super.onStart()
        viewModel.hideAddButton.observe(viewLifecycleOwner) {
            fabButton.tag = getString(it.tag)
            fabButton.setImageResource(it.image)
            bindind.coordinator.transitionName =  getString(it.transitionName)
        }
    }

    override fun onResume() {
        super.onResume()
        fabButton.show()
    }
}