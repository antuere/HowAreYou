package antuere.domain.usecases

import antuere.domain.Day
import antuere.domain.repository.DayRepository

class AddDayUseCase (private val dayRepository: DayRepository) {

    suspend operator fun invoke(day: Day) {
        dayRepository.insert(day)
    }
}