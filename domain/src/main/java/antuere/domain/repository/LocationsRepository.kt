package antuere.domain.repository

interface LocationsRepository {

    fun getSupportedCountries() : List<Int>

    fun getSupportedCities() : List<Int>
}