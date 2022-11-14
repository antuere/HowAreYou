package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository

class GetDayByIdUseCase(private val dayRepository: DayRepository) : UseCaseDefault<Day?, Long> {

    override suspend fun invoke(param: Long): Day? {
        return dayRepository.getDayById(param)
    }
}