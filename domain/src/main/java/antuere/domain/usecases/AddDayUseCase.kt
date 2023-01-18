package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository

class AddDayUseCase (private val dayRepository: DayRepository) : UseCaseDefault<Unit, Day> {

    override suspend fun invoke(param: Day) {
        dayRepository.insertLocal(param)
        dayRepository.insertRemote(param)
    }
}