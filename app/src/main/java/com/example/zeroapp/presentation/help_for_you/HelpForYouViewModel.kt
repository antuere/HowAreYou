package com.example.zeroapp.presentation.help_for_you

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject


//        TODO Удалить если не понадобится
@HiltViewModel
class HelpForYouViewModel @Inject constructor(

) : ViewModel() {

    init {
        Timber.i("animation error : init vm for helpfory screen id is ${this.hashCode()}")
    }
}