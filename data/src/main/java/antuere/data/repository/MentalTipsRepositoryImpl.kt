package antuere.data.repository

import antuere.data.R
import antuere.domain.dto.mental_tips.MentalTip
import antuere.domain.dto.mental_tips.MentalTipsCategory
import antuere.domain.dto.mental_tips.TipsCategoryName
import antuere.domain.repository.MentalTipsRepository
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
    MentalTip(
        imageRes = R.drawable.tips_current_times_0,
        titleRes = R.string.mental_tip_title_current_time_0,
        textRes = R.string.mental_tip_current_time_0
    ),
    MentalTip(
        imageRes = R.drawable.tips_current_times_1,
        titleRes = R.string.mental_tip_title_current_time_1,
        textRes = R.string.mental_tip_current_time_1
    ),
    MentalTip(
        imageRes = R.drawable.tips_current_times_2,
        titleRes = R.string.mental_tip_title_current_time_2,
        textRes = R.string.mental_tip_current_time_2
    ),
    MentalTip(
        imageRes = R.drawable.tips_current_times_3,
        titleRes = R.string.mental_tip_title_current_time_3,
        textRes = R.string.mental_tip_current_time_3
    ),
    MentalTip(
        imageRes = R.drawable.tips_current_times_4,
        titleRes = R.string.mental_tip_title_current_time_4,
        textRes = R.string.mental_tip_current_time_4
    ),
    MentalTip(
        imageRes = R.drawable.tips_current_times_5,
        titleRes = R.string.mental_tip_title_current_time_5,
        textRes = R.string.mental_tip_current_time_5
    )
)

private val mentalTipsYouSelf = listOf(
    MentalTip(
        imageRes = R.drawable.tips_youself_0,
        titleRes = R.string.mental_tip_title_youself_0,
        textRes = R.string.mental_tip_youself_0
    ),
    MentalTip(
        imageRes = R.drawable.tips_youself_1,
        titleRes = R.string.mental_tip_title_youself_1,
        textRes = R.string.mental_tip_youself_1
    ),
    MentalTip(
        imageRes = R.drawable.tips_youself_2,
        titleRes = R.string.mental_tip_title_youself_2,
        textRes = R.string.mental_tip_youself_2
    ),
    MentalTip(
        imageRes = R.drawable.tips_youself_3,
        titleRes = R.string.mental_tip_title_youself_3,
        textRes = R.string.mental_tip_youself_3
    ),
    MentalTip(
        imageRes = R.drawable.tips_youself_4,
        titleRes = R.string.mental_tip_title_youself_4,
        textRes = R.string.mental_tip_youself_4
    )
)

private val mentalTipsFamily = listOf(
    MentalTip(
        imageRes = R.drawable.tips_family_0,
        titleRes = R.string.mental_tip_title_family_0,
        textRes = R.string.mental_tip_family_0
    ),
    MentalTip(
        imageRes = R.drawable.tips_family_1,
        titleRes = R.string.mental_tip_title_family_1,
        textRes = R.string.mental_tip_family_1
    ),
    MentalTip(
        imageRes = R.drawable.tips_family_2,
        titleRes = R.string.mental_tip_title_family_2,
        textRes = R.string.mental_tip_family_2
    ),
    MentalTip(
        imageRes = R.drawable.tips_family_3,
        titleRes = R.string.mental_tip_title_family_3,
        textRes = R.string.mental_tip_family_3
    )
)

private val mentalTipsRelationship = listOf(
    MentalTip(
        imageRes = R.drawable.tips_relationship_0,
        titleRes = R.string.mental_tip_title_relationship_0,
        textRes = R.string.mental_tip_relationship_0
    ),
    MentalTip(
        imageRes = R.drawable.tips_relationship_1,
        titleRes = R.string.mental_tip_title_relationship_1,
        textRes = R.string.mental_tip_relationship_1
    ),
    MentalTip(
        imageRes = R.drawable.tips_relationship_2,
        titleRes = R.string.mental_tip_title_relationship_2,
        textRes = R.string.mental_tip_relationship_2
    ),
    MentalTip(
        imageRes = R.drawable.tips_relationship_3,
        titleRes = R.string.mental_tip_title_relationship_3,
        textRes = R.string.mental_tip_relationship_3
    ),
    MentalTip(
        imageRes = R.drawable.tips_relationship_4,
        titleRes = R.string.mental_tip_title_relationship_4,
        textRes = R.string.mental_tip_relationship_4
    ),
    MentalTip(
        imageRes = R.drawable.tips_relationship_5,
        titleRes = R.string.mental_tip_title_relationship_5,
        textRes = R.string.mental_tip_relationship_5
    ),
)

private val mentalTipsFriends = listOf(
    MentalTip(
        imageRes = R.drawable.tips_friends_0,
        titleRes = R.string.mental_tip_title_friends_0,
        textRes = R.string.mental_tip_friends_0
    ),
    MentalTip(
        imageRes = R.drawable.tips_friends_1,
        titleRes = R.string.mental_tip_title_friends_1,
        textRes = R.string.mental_tip_friends_1
    ),
    MentalTip(
        imageRes = R.drawable.tips_friends_2,
        titleRes = R.string.mental_tip_title_friends_2,
        textRes = R.string.mental_tip_friends_2
    ),
    MentalTip(
        imageRes = R.drawable.tips_friends_3,
        titleRes = R.string.mental_tip_title_friends_3,
        textRes = R.string.mental_tip_friends_3
    ),
)