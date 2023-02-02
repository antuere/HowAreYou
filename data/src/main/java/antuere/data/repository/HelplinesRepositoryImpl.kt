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
                    Helpline(
                        nameResId = R.string.helpline_rus_0,
                        phone = "8495051",
                        descResId = R.string.helpline_desc_rus_0,
                        website = "https://msph.ru/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_rus_1,
                        phone = "84959895050",
                        descResId = R.string.helpline_desc_rus_1,
                        website = "https://psi.mchs.gov.ru/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_rus_2,
                        phone = "88001016476",
                        descResId = R.string.helpline_desc_rus_2,
                        website = "https://tineodna.ru/#how"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_rus_3,
                        phone = "88007075465",
                        descResId = R.string.helpline_desc_rus_3,
                        website = "https://hotlineformen.ru/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_rus_4,
                        phone = "88002000122",
                        descResId = R.string.helpline_desc_rus_4,
                        website = "https://childhelpline.ru/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_rus_5,
                        phone = "88002000122",
                        descResId = R.string.helpline_desc_rus_5,
                        website = "https://www.xn--b1agja1acmacmce7nj.xn--80asehdb/"
                    ),
                ),
                flagResId = R.drawable.flag_ru
            ),
            SupportedCountry.USA(
                helplinesList = listOf(
                    Helpline(
                        nameResId = R.string.helpline_usa_0,
                        phone = "988",
                        descResId = R.string.helpline_desc_usa_0,
                        website = "https://988lifeline.org/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_usa_1,
                        phone = "741741",
                        descResId = R.string.helpline_desc_usa_1,
                        website = "https://www.crisistextline.org/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_usa_2,
                        phone = "18663319474",
                        descResId = R.string.helpline_desc_usa_2,
                        website = "https://www.loveisrespect.org/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_usa_3,
                        phone = "18664887386",
                        descResId = R.string.helpline_desc_usa_3,
                        website = "https://www.thetrevorproject.org/get-help/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_usa_4,
                        phone = "18447628483",
                        descResId = R.string.helpline_desc_usa_4,
                        website = "https://strongheartshelpline.org/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_usa_5,
                        phone = "18005224700",
                        descResId = R.string.helpline_desc_usa_5,
                        website = "https://www.ncpgambling.org/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_usa_6,
                        phone = "18002723900",
                        descResId = R.string.helpline_desc_usa_6,
                        website = "https://www.alz.org/help-support/resources/helpline"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_usa_7,
                        phone = "18002272345",
                        descResId = R.string.helpline_desc_usa_7,
                        website = "https://www.cancer.org/about-us/what-we-do/providing-support.html"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_usa_8,
                        phone = "18664825433",
                        descResId = R.string.helpline_desc_usa_8,
                        website = "https://www.internationalhelpline.org/"
                    ),
                ),
                flagResId = R.drawable.flag_us
            ),
            SupportedCountry.Italy(
                helplinesList = listOf(
                    Helpline(
                        nameResId = R.string.helpline_ita_0,
                        phone = "19696",
                        descResId = R.string.helpline_desc_ita_0,
                        website = "https://azzurro.it/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_ita_1,
                        phone = "0223272327",
                        descResId = R.string.helpline_desc_ita_1,
                        website = "https://www.telefonoamico.it/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_ita_2,
                        phone = "0299777",
                        descResId = R.string.helpline_desc_ita_2,
                        website = "https://telefonoamicocevita.it/"
                    ),
                    Helpline(
                        nameResId = R.string.helpline_ita_3,
                        phone = "1522",
                        descResId = R.string.helpline_desc_ita_3,
                        website = "https://www.1522.eu/"
                    ),
                ),
                flagResId = R.drawable.flag_ita
            ),
        )
    }


}