package com.example.zeroapp.historyFragment

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionManager
import com.example.zeroapp.R
import com.example.zeroapp.dataBase.Day
import com.example.zeroapp.dataBase.DayDatabase
import com.example.zeroapp.databinding.FragmentHistoryBinding
import com.example.zeroapp.util.buildMaterialDialog
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import timber.log.Timber

class HistoryFragment : Fragment(), DayClickListener {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var viewModel: HistoryViewModel
    private lateinit var bindind: FragmentHistoryBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        bindind = FragmentHistoryBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val dayDatabaseDao = DayDatabase.getInstance(application).dayDatabaseDao
        val factory = HistoryViewModelFactory(dayDatabaseDao)

        viewModel = ViewModelProvider(this, factory)[HistoryViewModel::class.java]

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
                    bindind.historyHint.visibility = View.INVISIBLE
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

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.duration_normal).toLong()
        }
    }


    override fun onClick(day: Day, view: View) {

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