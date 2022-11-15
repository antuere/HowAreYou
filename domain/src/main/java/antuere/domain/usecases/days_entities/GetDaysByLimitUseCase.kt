package antuere.domain.usecases.days_entities

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import antuere.domain.usecases.UseCaseDefault
import kotlinx.coroutines.flow.Flow


class GetDaysByLimitUseCase(private val dayRepository: DayRepository) :
    UseCaseDefault<Flow<List<Day>>, Int> {

    override suspend fun invoke(param: Int): Flow<List<Day>> {
        return dayRepository.getDaysByLimit(param)
    }
}