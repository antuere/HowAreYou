package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository

class UpdateDayUseCase(private val dayRepository: DayRepository) : UseCaseDefault<Unit, Day> {

    override suspend fun invoke(param: Day) {
        dayRepository.update(param)
    }

}