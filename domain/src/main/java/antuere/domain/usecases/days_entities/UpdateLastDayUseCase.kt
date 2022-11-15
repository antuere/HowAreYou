package antuere.domain.usecases.days_entities

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import antuere.domain.usecases.UseCaseDefault

class UpdateLastDayUseCase(private val dayRepository: DayRepository) : UseCaseDefault<Day?, Unit> {

    override suspend fun invoke(param: Unit): Day? {
        return dayRepository.getDay()
    }
}