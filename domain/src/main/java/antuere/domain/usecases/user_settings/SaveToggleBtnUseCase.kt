package antuere.domain.usecases.user_settings

import antuere.domain.dto.ToggleBtnState
import antuere.domain.repository.ToggleBtnRepository
import antuere.domain.usecases.UseCaseDefault

class SaveToggleBtnUseCase(private val toggleBtnRepository: ToggleBtnRepository) :
    UseCaseDefault<Unit, ToggleBtnState> {

    override suspend fun invoke(param: ToggleBtnState) {
        toggleBtnRepository.saveToggleButtonState(param)
    }
}