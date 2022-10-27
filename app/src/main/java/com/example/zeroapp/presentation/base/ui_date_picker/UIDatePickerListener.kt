package com.example.zeroapp.presentation.base.ui_date_picker

import androidx.core.util.Pair
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.google.android.material.datepicker.MaterialDatePicker
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UIDatePickerListener(
    private val supportFragmentManager: FragmentManager,
    private val action: IUIDatePickerAction
) {

    private var datePicker: MaterialDatePicker<Pair<Long, Long>>? = null

    private fun buildDatePicker(uiDatePicker: UIDatePicker): MaterialDatePicker<Pair<Long, Long>> {
        val picker = MaterialDatePicker.Builder.dateRangePicker()
            .setTitleText(uiDatePicker.title)
            .setSelection(
                Pair(
                    MaterialDatePicker.thisMonthInUtcMilliseconds(),
                    MaterialDatePicker.todayInUtcMilliseconds()
                )
            )
            .build()

        picker.addOnCancelListener {
            uiDatePicker.negativeButton.onClick.invoke()
        }

        picker.addOnNegativeButtonClickListener() {
            uiDatePicker.negativeButton.onClick.invoke()
        }

        picker.addOnPositiveButtonClickListener {
            uiDatePicker.positiveButton.onClick.invoke(it)
        }

        return picker
    }

    fun collect(lifecycleOwner: LifecycleOwner) {
        lifecycleOwner.lifecycleScope.launch(Dispatchers.Main) {
            action.datePicker.collectLatest { uiDatePicker ->
                if (uiDatePicker == null) {
                    datePicker?.dismiss()
                    datePicker = null
                } else if (datePicker == null) {
                    datePicker = buildDatePicker(uiDatePicker).also {
                        it.show(
                            supportFragmentManager,
                            "date_picker"
                        )
                    }
                }
            }
        }
    }

}