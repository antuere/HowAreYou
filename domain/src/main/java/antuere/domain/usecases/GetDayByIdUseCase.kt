package antuere.domain.usecases

import antuere.domain.Day
import antuere.domain.repository.DayRepository

class GetDayByIdUseCase(private val dayRepository: DayRepository) {

    suspend operator fun invoke(dayId: Long): Day? {
        return dayRepository.getDayById(dayId)
    }
}