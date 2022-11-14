package antuere.domain.usecases

import antuere.domain.repository.DayRepository

class DeleteAllDaysLocalUseCase(private val dayRepository: DayRepository) : UseCaseDefault<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        dayRepository.deleteAllDaysLocal()
    }
}
