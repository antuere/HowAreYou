package antuere.domain.usecases.days_entities

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import antuere.domain.usecases.UseCaseDefault
import kotlinx.coroutines.flow.Flow

class GetCertainDaysUseCase(private val dayRepository: DayRepository) :
    UseCaseDefault<Flow<List<Day>>, Long> {

    override suspend fun invoke(param: Long): Flow<List<Day>> {
        return dayRepository.getCertainDays(param)
    }
}
