package antuere.domain.dto.helplines

sealed class SupportedCountry(
    val nameRes: Int,
    val helplines: List<Helpline>
) {
    data class Russia(val nameResId: Int, val helplinesList: List<Helpline>) :
        SupportedCountry(nameResId, helplinesList)

    data class USA(val nameResId: Int, val helplinesList: List<Helpline>) :
        SupportedCountry(nameResId, helplinesList)

    data class Italy(val nameResId: Int, val helplinesList: List<Helpline>) :
        SupportedCountry(nameResId, helplinesList)
}
