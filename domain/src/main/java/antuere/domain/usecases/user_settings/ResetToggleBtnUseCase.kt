package antuere.domain.usecases.user_settings

import antuere.domain.repository.ToggleBtnRepository
import antuere.domain.usecases.UseCaseDefault

class ResetToggleBtnUseCase(private val toggleBtnRepository: ToggleBtnRepository) :
    UseCaseDefault<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        toggleBtnRepository.resetToggleButtonState()
    }
}