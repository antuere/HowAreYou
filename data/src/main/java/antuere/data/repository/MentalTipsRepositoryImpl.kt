package antuere.data.repository

import antuere.data.R
import antuere.domain.dto.mental_tips.MentalTip
import antuere.domain.dto.mental_tips.MentalTipsCategory
import antuere.domain.dto.mental_tips.TipsCategoryName
import antuere.domain.repository.MentalTipsRepository
import antuere.domain.util.Constants
import javax.inject.Inject

class MentalTipsRepositoryImpl @Inject constructor() : MentalTipsRepository {

    override suspend fun getMentalTipsCategories(): List<MentalTipsCategory> {
        return listOf(
            MentalTipsCategory(
                categoryName = TipsCategoryName.CURRENT_TIMES,
                iconRes = R.drawable.current_times,
                textRes = R.string.current_times
            ),
            MentalTipsCategory(
                categoryName = TipsCategoryName.YOU_SELF,
                iconRes = R.drawable.you_self,
                textRes = R.string.you_self
            ),
            MentalTipsCategory(
                categoryName = TipsCategoryName.FAMILY,
                iconRes = R.drawable.family,
                textRes = R.string.family
            ),
            MentalTipsCategory(
                categoryName = TipsCategoryName.RELATIONSHIP,
                iconRes = R.drawable.relationship,
                textRes = R.string.relationship
            ),
            MentalTipsCategory(
                categoryName = TipsCategoryName.FRIENDS,
                iconRes = R.drawable.friends,
                textRes = R.string.friends
            ),
        )
    }

    override suspend fun getMentalTips(categoryName: TipsCategoryName): List<MentalTip> {
        return when (categoryName) {
            TipsCategoryName.CURRENT_TIMES -> mentalTipsCurrentTimes
            TipsCategoryName.YOU_SELF -> mentalTipsYouSelf
            TipsCategoryName.FAMILY -> mentalTipsFamily
            TipsCategoryName.RELATIONSHIP -> mentalTipsRelationship
            TipsCategoryName.FRIENDS -> mentalTipsFriends
        }
    }
}

private val mentalTipsCurrentTimes = listOf(
    MentalTip(imageRes = R.drawable.current_times, textRes = R.string.current_times)
)

private val mentalTipsYouSelf = listOf(
    MentalTip(imageRes = R.drawable.you_self, textRes = R.string.you_self)
)

private val mentalTipsFamily = listOf(
    MentalTip(imageRes = R.drawable.family, textRes = R.string.family)
)

private val mentalTipsRelationship = listOf(
    MentalTip(imageRes = R.drawable.relationship, textRes = R.string.relationship)
)

private val mentalTipsFriends = listOf(
    MentalTip(imageRes = R.drawable.friends, textRes = R.string.friends)
)