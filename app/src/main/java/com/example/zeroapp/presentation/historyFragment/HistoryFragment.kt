package com.example.zeroapp.presentation.historyFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionManager
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentHistoryBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject


@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel by viewModels<HistoryViewModel>()
    private lateinit var bindind: FragmentHistoryBinding

    @Inject
    lateinit var dialogDelete: MaterialAlertDialogBuilder

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

        val adapter = DayAdapter(viewModel)

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

        viewModel.navigateToDetail.observe(viewLifecycleOwner) {
            if (it) {
                val dayId = viewModel.dayId.value!!
                val extras = viewModel.extras.value!!
                findNavController()
                    .navigate(
                        HistoryFragmentDirections.actionHistoryToDetailFragment(dayId),
                        extras
                    )

                viewModel.doneNavigateToDetail()
            }
        }

        viewModel.showDialogDelete.observe(viewLifecycleOwner) {
            if (it) {
                dialogDelete.setPositiveButton(R.string.yes) { dialog, _ ->
                    viewModel.deleteDay()
                    dialog.dismiss()
                }
                dialogDelete.show()
                viewModel.doneShowDialogDelete()
            }
        }

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        Timber.i("my log viewCreated")

    }

}