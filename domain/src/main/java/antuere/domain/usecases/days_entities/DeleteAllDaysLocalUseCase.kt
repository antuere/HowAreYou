package antuere.domain.usecases.days_entities

import antuere.domain.repository.DayRepository
import antuere.domain.usecases.UseCaseDefault

class DeleteAllDaysLocalUseCase(private val dayRepository: DayRepository) :
    UseCaseDefault<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        dayRepository.deleteAllDaysLocal()
    }
}
