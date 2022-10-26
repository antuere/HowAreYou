package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow

class GetSelectedDaysUseCase(private val dayRepository: DayRepository) :
    UseCase<Flow<List<Day>>, Pair<Long, Long>> {

    override suspend fun invoke(param: Pair<Long, Long>): Flow<List<Day>> {
        return dayRepository.getSelectedDays(param.first, param.second)
    }
}