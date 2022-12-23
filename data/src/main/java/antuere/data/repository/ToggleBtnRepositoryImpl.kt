package antuere.data.repository

import antuere.data.preferences_data_store.toggle_btn_data_store.ToggleBtnDataStore
import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.ToggleBtnRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToggleBtnRepositoryImpl @Inject constructor(
    private val toggleBtnDataStore: ToggleBtnDataStore,
) :
    ToggleBtnRepository {

    override suspend fun getToggleButtonState(): Flow<ToggleBtnState> =
        withContext(Dispatchers.IO) {
            toggleBtnDataStore.toggleButtonState
        }

    override suspend fun saveToggleButtonState(state: ToggleBtnState) =
        withContext(Dispatchers.IO) {
            toggleBtnDataStore.saveToggleButtonState(state.name)
        }

    override suspend fun resetToggleButtonState() {
        withContext(Dispatchers.IO) {
            toggleBtnDataStore.resetToggleButtonState()
        }
    }
}
