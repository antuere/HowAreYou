package antuere.how_are_you.presentation.screens.history.ui_compose.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import antuere.domain.dto.ToggleBtnState
import antuere.domain.util.TimeUtility
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.card.GradientCard
import antuere.how_are_you.presentation.base.ui_text.UiText
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults
import antuere.how_are_you.presentation.base.ui_theme.color_calendar_header
import antuere.how_are_you.presentation.screens.history.HelperForHistory

@Composable
fun CalendarImage(
    modifier: Modifier = Modifier,
    @DrawableRes calendarImageRes: Int,
    toggleBtnState: ToggleBtnState,
    description: UiText,
) {
    val context = LocalContext.current
    val headerText = remember(toggleBtnState) {
        if (toggleBtnState == ToggleBtnState.CURRENT_MONTH)
            UiText.StringResource(HelperForHistory.getHeaderForCalendar(TimeUtility.getMonth()))
        else UiText.String("${context.getString(R.string.calendar_week)} ${TimeUtility.getWeekNumber()}")
    }

    GradientCard(
        modifier = modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_normal_1)),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(),
        gradient = GradientDefaults.tertiary()
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        Image(
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.calendar_header),
            contentDescription = "Calendar header",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .background(color_calendar_header)
                .padding(vertical = dimensionResource(id = R.dimen.padding_small_1))
                .align(Alignment.CenterHorizontally),
            text = headerText.asString(),
            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )

        Image(
            modifier = Modifier
                .fillMaxWidth(0.5F)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = calendarImageRes),
            contentDescription = "Calendar image",
            contentScale = ContentScale.Crop
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(0.85F)
                .padding(dimensionResource(id = R.dimen.padding_normal_1)),
            text = description.asString(),
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
            textAlign = TextAlign.Center
        )
    }

}