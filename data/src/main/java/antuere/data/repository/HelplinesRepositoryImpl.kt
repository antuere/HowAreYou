package antuere.data.repository

import antuere.data.R
import antuere.domain.dto.helplines.Helpline
import antuere.domain.dto.helplines.SupportedCountry
import antuere.domain.repository.HelplinesRepository
import javax.inject.Inject

class HelplinesRepositoryImpl @Inject constructor() : HelplinesRepository {

    override suspend fun getSupportedCountries(): List<SupportedCountry> {
        return listOf(
            SupportedCountry.Russia(
                helplinesList = listOf(
                    Helpline(nameResId = R.string.helpline_rus_0, phone = "89289096581"),
                    Helpline(nameResId = R.string.helpline_rus_1, phone = "+79289645021"),
                    Helpline(nameResId = R.string.helpline_rus_2, phone = "238-78-55"),
                    Helpline(nameResId = R.string.helpline_rus_3, phone = "250 55 35"),
                ),
                flagResId = R.drawable.flag_ru
            ),
            SupportedCountry.USA(
                helplinesList = listOf(
                    Helpline(nameResId = R.string.helpline_usa_0, phone = "+19912875122"),
                    Helpline(nameResId = R.string.helpline_usa_1, phone = "99958200147925"),
                    Helpline(nameResId = R.string.helpline_usa_2, phone = "8922078914215800"),
                ),
                flagResId = R.drawable.flag_us
            ),
            SupportedCountry.Italy(
                helplinesList = listOf(
                    Helpline(nameResId = R.string.helpline_ita_0, phone = "78891021"),
                    Helpline(nameResId = R.string.helpline_ita_1, phone = "98703854795"),
                    Helpline(nameResId = R.string.helpline_ita_2, phone = "56871028-22"),
                    Helpline(nameResId = R.string.helpline_ita_3, phone = "89400187451"),
                    Helpline(nameResId = R.string.helpline_ita_4, phone = "777010117"),
                ),
                flagResId = R.drawable.flag_ita
            ),
        )
    }


}