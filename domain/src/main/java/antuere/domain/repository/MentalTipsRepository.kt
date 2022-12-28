package antuere.domain.repository

import antuere.domain.dto.mental_tips.MentalTip
import antuere.domain.dto.mental_tips.MentalTipsCategory

interface MentalTipsRepository {

    suspend fun getMentalTipsCategories(): List<MentalTipsCategory>

    suspend fun getMentalTips(categoryName: String): List<MentalTip>

}