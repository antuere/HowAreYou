package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow

class GetRequiresDaysUseCase(private val dayRepository: DayRepository) :
    UseCase<Flow<List<Day>>, Long> {

    override suspend fun invoke(param: Long): Flow<List<Day>> {
        return dayRepository.getRequiresDays(param)
    }
}
