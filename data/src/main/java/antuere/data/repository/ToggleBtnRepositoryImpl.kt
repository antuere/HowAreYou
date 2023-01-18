package antuere.data.repository

import antuere.data.preferences_data_store.toggle_btn_data_store.ToggleBtnDataStore
import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.ToggleBtnRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ToggleBtnRepositoryImpl @Inject constructor(
    private val toggleBtnDataStore: ToggleBtnDataStore
) :
    ToggleBtnRepository {

    override suspend fun getToggleButtonState(): Flow<ToggleBtnState> {
        return toggleBtnDataStore.toggleBtnConfiguration.flow
    }


    override suspend fun saveToggleButtonState(state: ToggleBtnState) {
        toggleBtnDataStore.toggleBtnConfiguration.set(state)
    }


    override suspend fun resetToggleButtonState() {
        toggleBtnDataStore.toggleBtnConfiguration.reset()
    }
}
