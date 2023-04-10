package antuere.how_are_you.presentation.screens.add_day.ui_compose

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.DefaultTextField
import antuere.how_are_you.presentation.base.ui_theme.PlayfairDisplay
import antuere.how_are_you.presentation.screens.add_day.state.AddDayIntent
import antuere.how_are_you.presentation.screens.add_day.state.AddDayState
import antuere.how_are_you.presentation.screens.add_day.ui_compose.components.SmileRow
import antuere.how_are_you.util.extensions.bringIntoViewForFocused
import antuere.how_are_you.util.extensions.paddingTopBar
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AddDayScreenState(
    viewState: () -> AddDayState,
    onIntent: (AddDayIntent) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar()
            .imePadding()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.1F))
        Text(
            text = stringResource(id = R.string.how_are_you_today),
            fontSize = dimensionResource(id = R.dimen.textSize_big_1).value.sp,
            fontFamily = PlayfairDisplay
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_8)))

        DefaultTextField(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2))
                .fillMaxWidth()
                .weight(0.7F)
                .bringIntoViewForFocused(
                    bringIntoViewRequester = bringIntoViewRequester,
                    scope = scope
                ),
            label = stringResource(id = R.string.desc_you_day),
            value = viewState().dayDesc,
            onValueChange = { onIntent(AddDayIntent.DayDescChanged(it)) },
            maxLength = 1000,
        )
        Spacer(
            modifier = Modifier
                .weight(0.2F)
        )

        SmileRow(
            modifier = Modifier.bringIntoViewRequester(bringIntoViewRequester),
            smileImages = viewState().smileImages.toImmutableList(),
            onClick = { onIntent(AddDayIntent.SmileClicked(it)) })

        Spacer(modifier = Modifier.weight(0.25F))
    }
}