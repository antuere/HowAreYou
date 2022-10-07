package antuere.domain.usecases

import antuere.domain.Day
import antuere.domain.repository.DayRepository

class UpdateLastDayUseCase(private val dayRepository: DayRepository) {

    suspend operator fun invoke(): Day? {
        return dayRepository.getDay()
    }
}