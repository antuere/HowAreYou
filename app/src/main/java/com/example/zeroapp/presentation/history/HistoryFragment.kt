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
import antuere.domain.dto.ToggleBtnState
import com.example.zeroapp.R
import com.example.zeroapp.databinding.FragmentHistoryBinding
import com.example.zeroapp.presentation.base.BaseBindingFragment
import com.example.zeroapp.presentation.base.ui_date_picker.UIDatePickerListener
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener
import com.example.zeroapp.presentation.history.adapter.DayAdapter
import com.example.zeroapp.util.mainActivity
import com.example.zeroapp.util.setManagerSpanCount
import com.google.android.material.transition.MaterialFade
import com.google.android.material.transition.MaterialFadeThrough
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HistoryFragment :
    BaseBindingFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    @Inject
    lateinit var myAnalystForHistory: MyAnalystForHistory

    private val viewModel by viewModels<HistoryViewModel>()

    private val dialogListener: UIDialogListener by lazy {
        UIDialogListener(requireContext(), viewModel)
    }

    private val datePickerListener: UIDatePickerListener by lazy {
        UIDatePickerListener(requireActivity().supportFragmentManager, viewModel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        exitTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.duration_normal).toLong()
        }

        enterTransition = MaterialFadeThrough().apply {
            duration = resources.getInteger(R.integer.duration_normal).toLong()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHistoryBinding.inflate(inflater, container, false)

        val adapter =  DayAdapter(viewModel.dayClickListener, myAnalystForHistory)
        val manager = GridLayoutManager(mainActivity!!, 4)

        binding!!.dayList.layoutManager = manager
        binding!!.dayList.adapter = adapter

        binding!!.toggleButton.addOnButtonCheckedListener { group, _, _ ->
            when (group.checkedButtonId) {
                R.id.button_all_days -> viewModel.onClickCheckedItem(ToggleBtnState.AllDays(1))
                R.id.button_current_month -> viewModel.onClickCheckedItem(ToggleBtnState.CurrentMonth(2))
                R.id.button_last_week -> viewModel.onClickCheckedItem(ToggleBtnState.LastWeek(3))
            }
        }
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

        viewModel.isFilterSelected.observe(viewLifecycleOwner) {
            if(it) {
                manager.setManagerSpanCount(3)
                binding!!.toggleButton.clearChecked()
                viewModel.resetIsFilterSelected()
            }
        }

        viewModel.toggleBtnState.observe(viewLifecycleOwner) {
            it?.let { state ->
                when (state) {
                    is ToggleBtnState.AllDays -> {
                        viewModel.checkedAllDaysButton()

                        manager.setManagerSpanCount(4)
                        binding!!.toggleButton.check(R.id.button_all_days)
                    }
                    is ToggleBtnState.LastWeek -> {
                        viewModel.checkedLastWeekButton()

                        manager.setManagerSpanCount(2)
                        binding!!.toggleButton.check(R.id.button_last_week)
                    }
                    is ToggleBtnState.CurrentMonth -> {
                        viewModel.checkedCurrentMonthButton()

                        manager.setManagerSpanCount(3)
                        binding!!.toggleButton.check(R.id.button_current_month)
                    }
                }
            }
        }

        return binding!!.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        postponeEnterTransition()
        view.doOnPreDraw {
            startPostponedEnterTransition()
        }
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

        buildMenu()
    }

    private fun buildMenu() {
        val menuHost: MenuHost = mainActivity!!
        menuHost.addMenuProvider(object : MenuProvider {

            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.history_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.filter_item -> {
                        viewModel.onClickFilterButton()
                        true
                    }
                    else -> false
                }
            }

        }, viewLifecycleOwner, Lifecycle.State.STARTED)
    }


    override fun onDestroyView() {
        binding!!.dayList.adapter = null
        binding = null

        super.onDestroyView()
    }
}