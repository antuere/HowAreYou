package antuere.domain.repository

import antuere.domain.dto.helplines.SupportedCountry

interface HelplinesRepository {

  suspend  fun getSupportedCountries() : List<SupportedCountry>

}