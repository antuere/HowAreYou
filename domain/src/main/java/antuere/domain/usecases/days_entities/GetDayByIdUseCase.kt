package antuere.domain.usecases.days_entities

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import antuere.domain.usecases.UseCaseDefault

class GetDayByIdUseCase(private val dayRepository: DayRepository) : UseCaseDefault<Day?, Long> {

    override suspend fun invoke(param: Long): Day? {
        return dayRepository.getDayById(param)
    }
}