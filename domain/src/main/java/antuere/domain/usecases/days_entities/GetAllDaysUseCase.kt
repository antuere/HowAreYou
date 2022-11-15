package antuere.domain.usecases.days_entities

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import antuere.domain.usecases.UseCaseDefault
import kotlinx.coroutines.flow.Flow

class GetAllDaysUseCase(private val dayRepository: DayRepository) :
    UseCaseDefault<Flow<List<Day>>, Unit> {

    override suspend fun invoke(param: Unit): Flow<List<Day>> {
        return dayRepository.getAllDays()
    }
}