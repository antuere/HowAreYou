package com.example.zeroapp.presentation.favorites

import android.view.View
import androidx.lifecycle.*
import androidx.navigation.fragment.FragmentNavigatorExtras
import antuere.domain.dto.Day
import antuere.domain.usecases.days_entities.DeleteDayUseCase
import antuere.domain.usecases.days_entities.GetFavoritesDaysUseCase
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_dialog.IUIDialogAction
import com.example.zeroapp.presentation.base.ui_dialog.UIDialog
import com.example.zeroapp.presentation.history.NavigateToDetailState
import com.example.zeroapp.presentation.history.adapter.DayClickListener
import com.example.zeroapp.util.toMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoritesDaysUseCase: GetFavoritesDaysUseCase,
    private val deleteDayUseCase: DeleteDayUseCase,
    private val transitionName: String
    ) : ViewModel(), IUIDialogAction {

    private var _uiDialog = MutableStateFlow<UIDialog?>(null)
    override val uiDialog: StateFlow<UIDialog?>
        get() = _uiDialog

    private var _listDays = MutableLiveData<List<Day>>()
    val listDays: LiveData<List<Day>>
        get() = _listDays

    private var _dayId = 0L

    private var _navigateToDetailState = MutableLiveData<NavigateToDetailState>()
    val navigateToDetailState: LiveData<NavigateToDetailState>
        get() = _navigateToDetailState

    init {
        viewModelScope.launch {
            _listDays =
                getFavoritesDaysUseCase(Unit).asLiveData(Dispatchers.Main)
                    .toMutableLiveData()
        }
    }

    val dayClickListener = object : DayClickListener {

        override fun onClick(day: Day, view: View) {
            val extras = FragmentNavigatorExtras(view to transitionName)

            _navigateToDetailState.value = NavigateToDetailState(
                extras = extras,
                dayId = day.dayId,
                navigateToDetail = true
            )
        }

        override fun onClickLong(day: Day) {
            _dayId = day.dayId
            onClickLongSmile()
        }
    }

    fun doneNavigateToDetail() {
        _navigateToDetailState.value!!.navigateToDetail = false
    }

    fun onClickLongSmile() {
        _uiDialog.value = UIDialog(
            title = R.string.dialog_delete_title,
            desc = R.string.dialog_delete_desc,
            icon = R.drawable.ic_delete_black,
            positiveButton = UIDialog.UiButton(
                text = R.string.yes,
                onClick = {
                    deleteDay()
                    _uiDialog.value = null
                }),
            negativeButton = UIDialog.UiButton(
                text = R.string.no,
                onClick = {
                    _uiDialog.value = null
                })
        )
    }

    private fun deleteDay() {
        viewModelScope.launch {
            deleteDayUseCase(_dayId)
        }
    }
}