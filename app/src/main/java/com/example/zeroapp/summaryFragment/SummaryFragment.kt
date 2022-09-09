package com.example.zeroapp.summaryFragment

import android.graphics.Color
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.transition.TransitionManager
import com.example.zeroapp.MainActivity
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.DayDatabase

import com.example.zeroapp.databinding.FragmentSummaryBinding
import com.example.zeroapp.themeColor
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import kotlinx.coroutines.delay
import timber.log.Timber

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

//        viewModel.hideAddButton.observe(viewLifecycleOwner) {
//            if (it) {
//                bindind.fabAddButton.visibility = View.GONE
//            }
//        }
        postponeEnterTransition()

        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = 350L
        }

        bindind.fabAddButton.setOnClickListener {

            val transitionName = getString(R.string.transition_name_for_sum)
            val extras = FragmentNavigatorExtras(bindind.coordinator to transitionName)

            findNavController().navigate(
                SummaryFragmentDirections.actionSummaryFragmentToAddDayFragment(), extras
            )
        }

    }

    override fun onStart() {
        super.onStart()
        viewModel.hideAddButton.observe(viewLifecycleOwner) {
            if (it) {
                bindind.fabAddButton.visibility = View.GONE
            }
        }
    }

    override fun onResume() {
        super.onResume()
        Timber.i("fix! sum log: onResume")
        viewModel.hideAddButton.observe(viewLifecycleOwner) {
            if (!it) {
                bindind.fabAddButton.visibility = View.VISIBLE
            }
        }
    }

}