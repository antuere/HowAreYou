package antuere.domain.usecases

import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.ToggleBtnRepository
import kotlinx.coroutines.flow.Flow

class GetToggleBtnStateUseCase(private val toggleBtnRepository: ToggleBtnRepository) :
    UseCase<Flow<ToggleBtnState>, Unit> {

    override suspend fun invoke(param: Unit): Flow<ToggleBtnState> {
        return toggleBtnRepository.getToggleButtonState()
    }

}