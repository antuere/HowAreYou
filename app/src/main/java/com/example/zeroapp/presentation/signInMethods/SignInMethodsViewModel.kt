package com.example.zeroapp.presentation.signInMethods

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import antuere.data.remoteDataBase.FirebaseApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class SignInMethodsViewModel @Inject constructor(
    private val firebaseApi: FirebaseApi
) : ViewModel() {

    private var _isHasUser = MutableLiveData<Boolean>()
    val isHasUser: LiveData<Boolean>
        get() = _isHasUser

    init {
        checkCurrentAuth()
    }

    fun checkCurrentAuth() {
        _isHasUser.value = firebaseApi.isHasUser()
    }

    fun navigationDone() {
        _isHasUser.value = false
    }
}