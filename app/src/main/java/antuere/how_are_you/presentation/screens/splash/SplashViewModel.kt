package antuere.how_are_you.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import antuere.domain.repository.QuoteRepository
import antuere.domain.repository.SettingsRepository
import antuere.how_are_you.presentation.base.navigation.Screen
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val quoteRepository: QuoteRepository,
    private val settingsRepository: SettingsRepository,
) : ViewModel() {

    private var _isShowSplash = MutableStateFlow(true)
    val isShowSplash: StateFlow<Boolean>
        get() = _isShowSplash

    private var _isEnablePin = MutableStateFlow(false)
    val isEnablePin: StateFlow<Boolean>
        get() = _isEnablePin

    private var _startScreen = MutableStateFlow<Screen>(Screen.Home)
    val startScreen: StateFlow<Screen>
        get() = _startScreen

    init {
        viewModelScope.launch {
            defineStartScreen()
        }
    }

    private suspend fun defineStartScreen() {
        viewModelScope.launch(Dispatchers.IO) {
            val isFirstLaunch = settingsRepository.isFirstLaunch()

            if (isFirstLaunch) {
                _startScreen.value = Screen.Onboard
                _isShowSplash.value = false
                return@launch
            }

            settingsRepository.getPinSetting().collectLatest { isEnablePin ->
                _isEnablePin.value = isEnablePin

                if (isEnablePin && _isShowSplash.value) {
                    _startScreen.value = Screen.SecureEntry
                    _isShowSplash.value = false
                }
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            quoteRepository.updateQuoteRemote()
            if (_isShowSplash.value) {
                _isShowSplash.value = false
            }
        }
    }
}