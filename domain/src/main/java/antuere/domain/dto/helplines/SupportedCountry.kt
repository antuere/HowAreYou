package antuere.domain.dto.helplines

sealed class SupportedCountry(
    val id: Int,
    val flagId: Int,
    val helplines: List<Helpline>,
) {
    data class Russia(val helplinesList: List<Helpline>, val flagResId: Int) :
        SupportedCountry(1, flagResId, helplinesList)

    data class USA(val helplinesList: List<Helpline>, val flagResId: Int) :
        SupportedCountry(2, flagResId, helplinesList)

    data class Italy(val helplinesList: List<Helpline>, val flagResId: Int) :
        SupportedCountry(3, flagResId, helplinesList)

    data class France(val helplinesList: List<Helpline>, val flagResId: Int) :
        SupportedCountry(4, flagResId, helplinesList)

    data class China(val helplinesList: List<Helpline>, val flagResId: Int) :
        SupportedCountry(5, flagResId, helplinesList)
}
