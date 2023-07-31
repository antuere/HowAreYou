package antuere.how_are_you.presentation.screens.add_day.ui_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_theme.PlayfairDisplay
import antuere.how_are_you.presentation.screens.add_day.state.AddDayIntent
import antuere.how_are_you.presentation.screens.add_day.state.AddDayState
import antuere.how_are_you.presentation.screens.add_day.ui_compose.components.DayDescriptionField
import antuere.how_are_you.presentation.screens.add_day.ui_compose.components.SmileRow
import antuere.how_are_you.util.extensions.paddingTopBar
import kotlinx.collections.immutable.toImmutableList

@Composable
fun AddDayScreenContent(
    viewState: () -> AddDayState,
    onIntent: (AddDayIntent) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar()
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_6)))
        Text(
            text = stringResource(id = R.string.how_are_you_today),
            fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp,
            fontFamily = PlayfairDisplay
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_6)))

        DayDescriptionField(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2))
                .fillMaxWidth()
                .weight(0.85F),
            value = { viewState().dayDesc },
            onValueChange = { onIntent(AddDayIntent.DayDescChanged(it)) },
            isEnabled = { viewState().isEnableTextField }
        )
        Spacer(modifier = Modifier.weight(0.15F))

        SmileRow(
            smileImages = viewState().smileImages.toImmutableList(),
            onClick = { onIntent(AddDayIntent.SmileClicked(it)) }
        )

        Spacer(modifier = Modifier.weight(0.15F))
    }
}