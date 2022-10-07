package antuere.domain.usecases

import antuere.domain.repository.DayRepository

class DeleteDayUseCase(private val dayRepository: DayRepository) {

    suspend operator fun invoke(dayId: Long) {
        dayRepository.deleteDay(dayId)
    }
}