package antuere.domain.repository

import antuere.domain.dto.ToggleBtnState
import kotlinx.coroutines.flow.Flow

interface ToggleBtnRepository {

    suspend fun getToggleButtonState() : Flow<ToggleBtnState>

    suspend fun saveToggleButtonState(state : ToggleBtnState)
}