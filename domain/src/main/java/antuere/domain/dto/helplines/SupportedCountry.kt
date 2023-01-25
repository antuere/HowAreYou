package antuere.domain.dto.helplines

sealed class SupportedCountry(
    val id: Int,
    val helplines: List<Helpline>
) {
    data class Russia(val helplinesList: List<Helpline>) :
        SupportedCountry(1, helplinesList)

    data class USA(val helplinesList: List<Helpline>) :
        SupportedCountry(2, helplinesList)

    data class Italy(val helplinesList: List<Helpline>) :
        SupportedCountry(3, helplinesList)
}
