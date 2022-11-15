package antuere.domain.usecases.days_entities

import antuere.domain.repository.DayRepository
import antuere.domain.usecases.UseCaseDefault

class DeleteDayUseCase(private val dayRepository: DayRepository) : UseCaseDefault<Unit, Long> {

    override suspend fun invoke(param: Long) {
        dayRepository.deleteDay(param)
    }
}