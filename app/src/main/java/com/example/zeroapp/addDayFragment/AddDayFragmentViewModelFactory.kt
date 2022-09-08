package com.example.zeroapp.addDayFragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.zeroapp.dataBase.DayDatabaseDao

class AddDayFragmentViewModelFactory(private val databaseDao: DayDatabaseDao) :
    ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AddDayFragmentViewModel::class.java)) {
            return AddDayFragmentViewModel(databaseDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}



