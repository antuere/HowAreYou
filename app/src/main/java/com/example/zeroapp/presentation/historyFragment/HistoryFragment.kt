package com.example.zeroapp.presentation.historyFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionManager
import com.example.zeroapp.R
import antuere.domain.Day
import com.example.zeroapp.databinding.FragmentHistoryBinding
import com.example.zeroapp.util.buildMaterialDialog
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HistoryFragment : Fragment(), DayClickListener {

    private val viewModel by viewModels<HistoryViewModel>()
    private lateinit var bindind: FragmentHistoryBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("my log history on create")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("my log history on createView")
        bindind = FragmentHistoryBinding.inflate(inflater, container, false)

        val manager = GridLayoutManager(activity, 4)

        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> 4
                    else -> 1
                }
            }

        }
        bindind.dayList.layoutManager = manager

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.duration_normal).toLong()
        }

        val adapter = DayAdapter(this)

        bindind.dayList.adapter = adapter

        viewModel.listDays.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty()) {
                    val materialFade = MaterialFade().apply {
                        duration = 250L
                    }
                    TransitionManager.beginDelayedTransition(container!!, materialFade)

                    bindind.historyHint.visibility = View.VISIBLE
                } else {
                    bindind.historyHint.visibility = View.GONE
                }
                adapter.addHeaderAndSubmitList(it)
                Timber.i("my log Submit list")
            }
        }

        return bindind.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        Timber.i("my log viewCreated")

    }

    override fun onClick(day: Day, view: View) {
        Timber.i("my log history onClick")
        val transitionName = getString(R.string.transition_name)
        val extras = FragmentNavigatorExtras(view to transitionName)
        findNavController()
            .navigate(HistoryFragmentDirections.actionHistoryToDetailFragment(day.dayId), extras)
    }

    override fun onClickLong(day: Day) {
        val dialog =
            buildMaterialDialog({ dayId -> viewModel.deleteDay(dayId) }, day.dayId, this.context)
        dialog.show()
    }

}