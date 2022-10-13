package com.example.zeroapp.presentation.favorites

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.FragmentNavigatorExtras
import antuere.domain.dto.Day
import antuere.domain.usecases.GetFavoritesDaysUseCase
import com.example.zeroapp.presentation.history.NavigateToDetailState
import com.example.zeroapp.presentation.history.adapter.DayClickListener
import com.example.zeroapp.util.toMutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavoritesDaysUseCase: GetFavoritesDaysUseCase,
    private val transitionName: String,

    ) : ViewModel() {

    private var _listDays = MutableLiveData<List<Day>>()
    val listDays: LiveData<List<Day>>
        get() = _listDays

//    private var _dayId = 0L

    private var _navigateToDetailState = MutableLiveData<NavigateToDetailState>()
    val navigateToDetailState: LiveData<NavigateToDetailState>
        get() = _navigateToDetailState

    init {
        runBlocking {
            _listDays =
                getFavoritesDaysUseCase.invoke(Unit).asLiveData(Dispatchers.Main).toMutableLiveData()
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
        }
    }

    fun doneNavigateToDetail() {
        _navigateToDetailState.value!!.navigateToDetail = false
    }
}