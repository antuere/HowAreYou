package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository

class UpdateLastDayUseCase(private val dayRepository: DayRepository) : UseCase<Day?, Unit> {


    override suspend fun invoke(param: Unit): Day? {
        return dayRepository.getDay()
    }
}