package antuere.data.repository

import antuere.data.preferences_data_store.toggle_btn_data_store.ToggleBtnDataStore
import antuere.data.preferences_data_store.toggle_btn_data_store.mapping.ToggleBtnStateEntityMapper
import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.ToggleBtnRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject

class ToggleBtnRepositoryImpl @Inject constructor(
    private val toggleBtnDataStore: ToggleBtnDataStore,
    private val toggleBtnStateMapperPref: ToggleBtnStateEntityMapper
) :
    ToggleBtnRepository {

    override suspend fun getToggleButtonState(): Flow<ToggleBtnState> {
        return toggleBtnDataStore.toggleButtonState.map {
            Timber.i("toggle error: collect tgb btn state in rep")
            toggleBtnStateMapperPref.mapToDomainModel(it)
        }
    }

    override suspend fun saveToggleButtonState(state: ToggleBtnState) {
        toggleBtnDataStore.saveToggleButtonState(state.id)
    }
}
