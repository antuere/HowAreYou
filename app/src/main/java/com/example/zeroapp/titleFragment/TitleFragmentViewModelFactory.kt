package com.example.zeroapp.titleFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zeroapp.dataBase.DayDatabaseDao

class TitleFragmentViewModelFactory(private val databaseDao: DayDatabaseDao,
                                    val descDay : String) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TitleFragmentViewModel::class.java)) {
            return TitleFragmentViewModel(databaseDao, descDay) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



