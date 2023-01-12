package antuere.domain.dto.helplines

sealed class SupportedCountry(
    val id: Int,
    val helplines: List<Helpline>
) {
    data class Russia(val countryId: Int = 1, val helplinesList: List<Helpline>) :
        SupportedCountry(countryId, helplinesList)

    data class USA(val countryId: Int = 2, val helplinesList: List<Helpline>) :
        SupportedCountry(countryId, helplinesList)

    data class Italy(val countryId: Int = 3, val helplinesList: List<Helpline>) :
        SupportedCountry(countryId, helplinesList)
}
