package antuere.domain.usecases

import antuere.domain.repository.DayRepository

class UpdateFromFireBaseUseCase(private val dayRepository: DayRepository) : UseCase<Unit, Unit> {

    override suspend fun invoke(param: Unit) {
        dayRepository.updateDaysFromFireBase()
    }
}