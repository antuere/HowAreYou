package antuere.domain.usecases.days_entities

import antuere.domain.repository.DayRepository
import antuere.domain.usecases.UseCaseDefault

class DeleteAllDaysRemoteUseCase(private val dayRepository: DayRepository) :
    UseCaseDefault<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        dayRepository.deleteAllDaysRemote()
    }
}
