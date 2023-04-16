package antuere.how_are_you.presentation.screens.detail.ui_compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.screens.detail.state.DetailIntent
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun DetailScreenState(
    onIntent: (DetailIntent) -> Unit,
    isLoading: Boolean,
    isEditMode: Boolean,
    daySmileImage: Int,
    dateString: String,
    dayText: String,
    dayTextEditable: String,
) {
    if (isLoading) {
        FullScreenProgressIndicator()
    } else {
        Crossfade(isEditMode) { isEditModeOn ->
            when (isEditModeOn) {
                true -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .paddingTopBar()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
                        DaySmile(
                            modifier = Modifier.size(100.dp),
                            smileRes = { daySmileImage }
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

                        Text(
                            text = dateString,
                            fontSize = dimensionResource(id = R.dimen.textSize_big_1).value.sp
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

                        DayEditTextField(
                            modifier = Modifier.fillMaxWidth(0.8F),
                            value = { dayTextEditable },
                            onIntent = onIntent
                        )
                    }
                }

                false -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .paddingTopBar()
                            .verticalScroll(rememberScrollState()),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
                        DaySmile(
                            modifier = Modifier.size(100.dp),
                            smileRes = { daySmileImage }
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

                        Text(
                            text = dateString,
                            fontSize = dimensionResource(id = R.dimen.textSize_big_1).value.sp
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

                        Text(
                            modifier = Modifier.fillMaxWidth(0.8F),
                            text = dayText,
                            fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp
                        )
                    }
                }
            }
        }
    }
}