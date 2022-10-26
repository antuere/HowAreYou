package com.example.zeroapp.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.data.remote_day_database.FirebaseApi
import antuere.domain.usecases.RefreshRemoteDataUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val firebaseApi: FirebaseApi,
    private val refreshRemoteDataUseCase: RefreshRemoteDataUseCase
) : ViewModel() {

    private var _userNickname = MutableLiveData<String?>()
    val userNickname: LiveData<String?>
        get() = _userNickname

    private var _isHasUser = MutableLiveData<Boolean>()
    val isHasUser: LiveData<Boolean>
        get() = _isHasUser

    init {
        updateUserNickname()
        checkCurrentUser()
    }


    fun updateUserNickname() {
        viewModelScope.launch {
            val result = firebaseApi.getUserNicknameAsync().await()
            _userNickname.value = result
        }
    }

    fun checkCurrentUser() {
        _isHasUser.value = firebaseApi.isHasUser()
    }

    fun onSignOutClicked() {
        firebaseApi.auth.signOut()

        updateUserNickname()
        viewModelScope.launch {
            refreshRemoteDataUseCase.invoke(Unit)
        }
    }


}