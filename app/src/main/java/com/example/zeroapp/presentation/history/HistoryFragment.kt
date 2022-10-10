package com.example.zeroapp.presentation.history

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
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener
import com.example.zeroapp.presentation.history.adapter.DayAdapter
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber


@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel by viewModels<HistoryViewModel>()
    private lateinit var binding: FragmentHistoryBinding

    private val dialogListener: UIDialogListener by lazy {
        UIDialogListener(requireContext(), viewModel)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.i("my log history on create")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        Timber.i("my log history on createView")
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val manager = GridLayoutManager(activity, 4)

        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> 4
                    else -> 1
                }
            }

        }
        binding.dayList.layoutManager = manager

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.duration_normal).toLong()
        }

        val adapter = DayAdapter(viewModel.dayClickListener)

        binding.dayList.adapter = adapter

        viewModel.listDays.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty()) {
                    val materialFade = MaterialFade().apply {
                        duration = 250L
                    }
                    TransitionManager.beginDelayedTransition(container!!, materialFade)

                    binding.historyHint.visibility = View.VISIBLE
                } else {
                    binding.historyHint.visibility = View.GONE
                }
                adapter.addHeaderAndSubmitList(it)
                Timber.i("my log Submit list")
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        dialogListener.collect(this)

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

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }

        Timber.i("my log viewCreated")

    }

}