package antuere.domain.usecases.locations

import antuere.domain.repository.LocationsRepository
import antuere.domain.usecases.UseCaseDefault

class GetSupportedCountriesUseCase(private val locationsRepository: LocationsRepository) :
    UseCaseDefault<List<Int>, Unit> {

    override suspend fun invoke(param: Unit): List<Int> {
        return locationsRepository.getSupportedCountries()
    }
}