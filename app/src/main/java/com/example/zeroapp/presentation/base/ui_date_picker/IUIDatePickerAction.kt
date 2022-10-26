package com.example.zeroapp.presentation.base.ui_date_picker

import kotlinx.coroutines.flow.StateFlow

interface IUIDatePickerAction {
    val datePicker : StateFlow<UIDatePicker?>
}