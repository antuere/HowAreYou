package antuere.domain.usecases

import antuere.domain.repository.DayRepository

class RefreshRemoteDataUseCase(private val dayRepository: DayRepository) : UseCaseDefault<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        dayRepository.refreshRemoteData()
    }
}