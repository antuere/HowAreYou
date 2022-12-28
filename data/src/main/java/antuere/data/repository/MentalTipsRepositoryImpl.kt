package antuere.data.repository

import antuere.data.R
import antuere.domain.dto.mental_tips.MentalTip
import antuere.domain.dto.mental_tips.MentalTipsCategory
import antuere.domain.repository.MentalTipsRepository
import antuere.domain.util.Constants
import javax.inject.Inject

class MentalTipsRepositoryImpl @Inject constructor() : MentalTipsRepository {

    override suspend fun getMentalTipsCategories(): List<MentalTipsCategory> {
        return listOf(
            MentalTipsCategory(
                categoryName = Constants.CURRENT_TIMES_CATEGORY,
                iconRes = R.drawable.you_self,
                textRes = R.string.current_times
            ),
            MentalTipsCategory(
                categoryName = Constants.YOU_SELF_CATEGORY,
                iconRes = R.drawable.you_self,
                textRes = R.string.you_self
            ),
            MentalTipsCategory(
                categoryName = Constants.FAMILY_CATEGORY,
                iconRes = R.drawable.you_self,
                textRes = R.string.family
            ),
            MentalTipsCategory(
                categoryName = Constants.RELATIONSHIP_CATEGORY,
                iconRes = R.drawable.you_self,
                textRes = R.string.relationship
            ),
            MentalTipsCategory(
                categoryName = Constants.FRIENDS_CATEGORY,
                iconRes = R.drawable.friends,
                textRes = R.string.friends
            ),
        )
    }

    override suspend fun getMentalTips(categoryName: String): List<MentalTip> {
        TODO("Not yet implemented")
    }
}