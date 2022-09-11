package com.example.zeroapp.summaryFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.zeroapp.*
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentSummaryBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialElevationScale
import timber.log.Timber

class SummaryFragment : Fragment() {

    companion object {
        fun newInstance() = SummaryFragment()
    }

    private lateinit var viewModel: SummaryViewModel
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

        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao
        val factory = SummaryViewModelFactory(dayDatabaseDao)

        viewModel = ViewModelProvider(this, factory)[SummaryViewModel::class.java]
        viewModel.updateLastDay()

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        fabButton = bindind.fabAddButton
        fabButton.setOnClickListener {

            reenterTransition = MaterialElevationScale(true).apply {
                duration = 350L
            }

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

        viewModel.hideAddButton.observe(viewLifecycleOwner) {
            if (it) {
                fabButton.setImageResource(getSmileImage(viewModel.lastDay.value!!.imageId))
                fabButton.tag = getString(R.string.smile)

            } else {
                fabButton.setImageResource(R.drawable.ic_plus)
                fabButton.tag = getString(R.string.add)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.hideAddButton.observe(viewLifecycleOwner) {
            if (it) {
                bindind.coordinator.transitionName = getString(R.string.transition_name)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.hideAddButton.observe(viewLifecycleOwner) {
            if (!it) {
                bindind.coordinator.transitionName = getString(R.string.transition_name_for_sum)
            }
        }

    }
}