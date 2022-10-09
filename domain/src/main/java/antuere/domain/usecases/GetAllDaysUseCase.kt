package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow

class GetAllDaysUseCase(private val dayRepository: DayRepository) : UseCase<Flow<List<Day>>, Unit> {
    override suspend fun run(param: Unit): Flow<List<Day>> {
        return dayRepository.getAllDays()
    }
}