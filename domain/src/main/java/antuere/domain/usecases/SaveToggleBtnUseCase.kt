package antuere.domain.usecases

import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.ToggleBtnRepository

class SaveToggleBtnUseCase(private val toggleBtnRepository: ToggleBtnRepository) :
    UseCase<Unit, ToggleBtnState> {

    override suspend fun invoke(param: ToggleBtnState) {
        toggleBtnRepository.saveToggleButtonState(param)
    }
}