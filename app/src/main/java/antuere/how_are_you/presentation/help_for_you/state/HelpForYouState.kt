package antuere.how_are_you.presentation.help_for_you.state

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText

data class HelpForYouState(
    val titleText: UiText = UiText.StringResource(R.string.help_for_you_title_card),
    val helplinesCard: HelpForYouCard = HelpForYouCard(
        nameRes = R.string.helplines,
        leadIconRes = R.drawable.ic_hotlines,
        trailingIconRes = R.drawable.ic_round_chevron_right
    ),
    val telegramCard: HelpForYouCard = HelpForYouCard(
        nameRes = R.string.telegram_chat,
        leadIconRes = R.drawable.ic_telegram
    ),
    val emailCard: HelpForYouCard = HelpForYouCard(
        nameRes = R.string.email_chat,
        leadIconRes = R.drawable.ic_email
    ),
)

data class HelpForYouCard(
    @StringRes val nameRes: Int,
    @DrawableRes val leadIconRes: Int,
    @DrawableRes val trailingIconRes: Int? = null,
)
