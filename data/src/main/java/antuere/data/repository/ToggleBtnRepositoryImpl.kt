package antuere.data.repository

import antuere.data.preferences_data_store.toggle_btn_data_store.ToggleBtnDataStore
import antuere.data.preferences_data_store.toggle_btn_data_store.mapping.ToggleBtnStateEntityMapper
import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.ToggleBtnRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ToggleBtnRepositoryImpl @Inject constructor(
    private val toggleBtnDataStore: ToggleBtnDataStore,
    private val toggleBtnStateMapperPref: ToggleBtnStateEntityMapper
) :
    ToggleBtnRepository {

    override suspend fun getToggleButtonState(): Flow<ToggleBtnState> = withContext(Dispatchers.IO) {
         toggleBtnDataStore.toggleButtonState.map {
            toggleBtnStateMapperPref.mapToDomainModel(it)
        }
    }

    override suspend fun saveToggleButtonState(state: ToggleBtnState) = withContext(Dispatchers.IO) {
        toggleBtnDataStore.saveToggleButtonState(state.id)
    }
}
