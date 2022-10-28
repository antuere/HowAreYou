package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow

class GetCertainDaysUseCase(private val dayRepository: DayRepository) :
    UseCase<Flow<List<Day>>, Long> {

    override suspend fun invoke(param: Long): Flow<List<Day>> {
        return dayRepository.getCertainDays(param)
    }
}
