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
import com.example.zeroapp.util.setManagerSpanCount
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFade
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HistoryFragment :
    BaseBindingFragment<FragmentHistoryBinding>(FragmentHistoryBinding::inflate) {

    @Inject
    lateinit var myAnalystForHistory: MyAnalystForHistory

    private val adapter: DayAdapter by lazy {
        DayAdapter(viewModel.dayClickListener, myAnalystForHistory)
    }

    private val mainActivity: MainActivity by lazy {
        requireActivity() as MainActivity
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

        binding!!.dayList.layoutManager = manager
        binding!!.dayList.adapter = adapter

        viewModel.getToggleButtonState()

        binding!!.toggleButton.addOnButtonCheckedListener { group, _, _ ->
            when (group.checkedButtonId) {
                R.id.button_all_days -> viewModel.onClickCheckedItem(ToggleButtonDataStore.CHECKED_ALL_DAYS)
                R.id.button_current_month -> viewModel.onClickCheckedItem(ToggleButtonDataStore.CHECKED_CURRENT_MONTH)
                R.id.button_last_week -> viewModel.onClickCheckedItem(ToggleButtonDataStore.CHECKED_LAST_WEEK)
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

        viewModel.toggleButtonState.observe(viewLifecycleOwner) {
            it?.let { state ->
                when (state) {
                    is ToggleButtonState.Filter -> {
                        viewModel.checkedFilterButton(state.pair)

                        manager.setManagerSpanCount(3)
                        binding!!.toggleButton.clearChecked()
                    }
                    is ToggleButtonState.AllDays -> {
                        viewModel.checkedAllDaysButton()

                        manager.setManagerSpanCount(4)
                        binding!!.toggleButton.check(R.id.button_all_days)
                    }
                    is ToggleButtonState.LastWeek -> {
                        viewModel.checkedLastWeekButton()

                        manager.setManagerSpanCount(2)
                        binding!!.toggleButton.check(R.id.button_last_week)
                    }
                    is ToggleButtonState.CurrentMonth -> {
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
        val menuHost: MenuHost = mainActivity
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
}