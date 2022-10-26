package com.example.zeroapp.presentation.history

import android.os.Bundle
import android.view.*
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.transition.TransitionManager
import com.example.zeroapp.MainActivity
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentHistoryBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.presentation.base.ui_date_picker.UIDatePickerListener
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener
import com.example.zeroapp.presentation.history.adapter.DayAdapter
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay


@AndroidEntryPoint
class HistoryFragment :
    BaseBindingFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    private val mainActivity: MainActivity by lazy {
        requireActivity() as MainActivity
    }

    private val adapter: DayAdapter by lazy {
        DayAdapter(viewModel.dayClickListener)
    }

    private val viewModel by viewModels<HistoryViewModel>()

    private val dialogListener: UIDialogListener by lazy {
        UIDialogListener(requireContext(), viewModel)
    }

    private val datePickerListener: UIDatePickerListener by lazy {
        UIDatePickerListener(requireActivity().supportFragmentManager, viewModel)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.duration_normal).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val manager = GridLayoutManager(mainActivity, 4)
        manager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return when (position) {
                    0 -> 4
                    else -> 1
                }
            }

        }
        binding!!.dayList.layoutManager = manager

        binding!!.dayList.adapter = adapter

        viewModel.listDays.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty()) {
                    val materialFade = MaterialFade().apply {
                        duration = 250L
                    }
                    TransitionManager.beginDelayedTransition(container!!, materialFade)

                    binding!!.historyHint.visibility = View.VISIBLE
                } else {
                    binding!!.historyHint.visibility = View.GONE
                }
                adapter.addHeaderAndSubmitList(it)
            }
        }



        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        dialogListener.collect(this)
        datePickerListener.collect(this)

        viewModel.navigateToDetailState.observe(viewLifecycleOwner) {
            it?.let { state ->
                if (state.navigateToDetail) {
                    findNavController()
                        .navigate(
                            HistoryFragmentDirections.actionHistoryToDetailFragment(state.dayId!!),
                            state.extras!!
                        )
                    viewModel.doneNavigateToDetail()
                }
            }
        }

        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
        buildMenu()
    }

    private fun buildMenu() {
        val menuHost: MenuHost = mainActivity
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.history_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.filter_item -> {
                      val picker =  MaterialDatePicker.Builder.dateRangePicker()
                          .setTitleText(R.string.date_picker_title)
                          .build()

                        picker.addOnPositiveButtonClickListener{
                            val kotlinPair: Pair<Long, Long> = Pair(it.first, it.second)
                            viewModel.onClickFilterButtonTest(kotlinPair)
                        }

                        picker.show(requireActivity().supportFragmentManager, "date_picker_test")
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.STARTED)
    }

}