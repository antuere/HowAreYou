package antuere.domain.usecases

import antuere.domain.Day
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow

class GetAllDaysUseCase(private val dayRepository: DayRepository) {
    operator fun invoke(): Flow<List<Day>> {
        return dayRepository.getAllDays()
    }
}