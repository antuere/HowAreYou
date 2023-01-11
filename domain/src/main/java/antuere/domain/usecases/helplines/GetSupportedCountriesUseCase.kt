package antuere.domain.usecases.helplines

import antuere.domain.dto.helplines.SupportedCountry
import antuere.domain.repository.HelplinesRepository
import antuere.domain.usecases.UseCaseDefault

class GetSupportedCountriesUseCase(private val helplinesRepository: HelplinesRepository) :
    UseCaseDefault<List<SupportedCountry>, Unit> {

    override suspend fun invoke(param: Unit): List<SupportedCountry> {
        return helplinesRepository.getSupportedCountries()
    }
}