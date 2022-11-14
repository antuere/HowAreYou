package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow

class GetAllDaysUseCase(private val dayRepository: DayRepository) : UseCaseDefault<Flow<List<Day>>, Unit> {

    override suspend fun invoke(param: Unit): Flow<List<Day>> {
        return dayRepository.getAllDays()
    }
}