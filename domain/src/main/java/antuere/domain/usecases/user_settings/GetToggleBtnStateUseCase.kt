package antuere.domain.usecases.user_settings

import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.ToggleBtnRepository
import antuere.domain.usecases.UseCaseDefault
import kotlinx.coroutines.flow.Flow

class GetToggleBtnStateUseCase(private val toggleBtnRepository: ToggleBtnRepository) :
    UseCaseDefault<Flow<ToggleBtnState>, Unit> {

    override suspend fun invoke(param: Unit): Flow<ToggleBtnState> {
        return toggleBtnRepository.getToggleButtonState()
    }

}