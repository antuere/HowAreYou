package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository

class GetDayByIdUseCase(private val dayRepository: DayRepository) : UseCase<Day?,Long, > {

    override suspend fun run(param: Long): Day? {
        return dayRepository.getDayById(param)
    }
}