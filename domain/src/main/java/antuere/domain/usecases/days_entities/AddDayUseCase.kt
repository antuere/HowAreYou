package antuere.domain.usecases.days_entities

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import antuere.domain.usecases.UseCaseDefault

class AddDayUseCase (private val dayRepository: DayRepository) : UseCaseDefault<Unit, Day> {

    override suspend fun invoke(param: Day) {
        dayRepository.insertLocal(param)
        dayRepository.insertRemote(param)
    }
}