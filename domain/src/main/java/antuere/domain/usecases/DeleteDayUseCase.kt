package antuere.domain.usecases

import antuere.domain.repository.DayRepository

class DeleteDayUseCase(private val dayRepository: DayRepository) : UseCase<Unit, Long> {

    override suspend fun invoke(param: Long) {
        dayRepository.deleteDay(param)
    }
}