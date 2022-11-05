package com.example.zeroapp.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.data.remote_day_database.FirebaseApi
import antuere.domain.dto.Settings
import antuere.domain.usecases.GetSettingsUseCase
import antuere.domain.usecases.RefreshRemoteDataUseCase
import antuere.domain.usecases.SaveSettingsUseCase
import com.example.zeroapp.presentation.base.ui_biometric_dialog.IUIBiometricListener
import com.example.zeroapp.presentation.summary.BiometricAuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val firebaseApi: FirebaseApi,
    private val refreshRemoteDataUseCase: RefreshRemoteDataUseCase,
    private val saveSettingsUseCase: SaveSettingsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel(), IUIBiometricListener {

    private var _userNickname = MutableLiveData<String?>()
    val userNickname: LiveData<String?>
        get() = _userNickname

    private var _settings = MutableLiveData<Settings?>()
    val settings: LiveData<Settings?>
        get() = _settings

    private var _biometricAuthState = MutableLiveData<BiometricAuthState>()
    val biometricAuthState: LiveData<BiometricAuthState>
        get() = _biometricAuthState

    init {
        updateUserNickname()
        getSettings()
    }

    fun updateUserNickname() {
        viewModelScope.launch {
            val result = firebaseApi.getUserNicknameAsync().await()
            _userNickname.value = result
        }
    }

    fun onSignOutClicked() {
        firebaseApi.auth.signOut()

        updateUserNickname()
        viewModelScope.launch {
            refreshRemoteDataUseCase(Unit)
        }
    }

    private fun getSettings() {
        viewModelScope.launch {
            getSettingsUseCase(Unit).collectLatest {
                _settings.postValue(it)
            }
        }
    }

    fun saveSettings(biometricEnabled: Boolean) {
        viewModelScope.launch {
            val newSettings = Settings(biometricEnabled)
            saveSettingsUseCase(newSettings)
        }
    }

    override fun onBiometricAuthFailed() {
        _biometricAuthState.value = BiometricAuthState.Error
    }

    override fun onBiometricAuthSuccess() {
        _biometricAuthState.value = BiometricAuthState.Successful
    }


}