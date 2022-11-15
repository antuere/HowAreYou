package antuere.domain.usecases.days_entities

import antuere.domain.repository.DayRepository
import antuere.domain.usecases.UseCaseDefault

class RefreshRemoteDataUseCase(private val dayRepository: DayRepository) :
    UseCaseDefault<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        dayRepository.refreshRemoteData()
    }
}