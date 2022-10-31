package antuere.domain.usecases

import antuere.domain.dto.Day
import antuere.domain.repository.DayRepository
import kotlinx.coroutines.flow.Flow


class GetDaysByLimitUseCase(private val dayRepository: DayRepository) :  UseCase<Flow<List<Day>>, Int>{

    override suspend fun invoke(param: Int): Flow<List<Day>> {
        return dayRepository.getDaysByLimit(param)
    }
}