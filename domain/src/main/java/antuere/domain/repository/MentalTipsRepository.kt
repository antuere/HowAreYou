package antuere.domain.repository

import antuere.domain.dto.mental_tips.MentalTip
import antuere.domain.dto.mental_tips.MentalTipsCategory
import antuere.domain.dto.mental_tips.TipsCategoryName

interface MentalTipsRepository {

    suspend fun getMentalTipsCategories(): List<MentalTipsCategory>

    suspend fun getMentalTips(categoryName: TipsCategoryName): List<MentalTip>

}