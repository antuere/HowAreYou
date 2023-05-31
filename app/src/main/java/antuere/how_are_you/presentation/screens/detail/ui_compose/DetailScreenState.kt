package antuere.how_are_you.presentation.screens.detail.ui_compose

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.screens.detail.state.DetailIntent
import antuere.how_are_you.util.extensions.paddingTopBar
import antuere.how_are_you.util.extensions.toStable
import kotlinx.collections.immutable.ImmutableList

@Composable
fun DetailScreenState(
    onIntent: (DetailIntent) -> Unit,
    isLoading: Boolean,
    isEditMode: Boolean,
    daySmileImage: Int,
    dayText: String,
    dayTextEditable: String,
    smileImages: ImmutableList<Int>,
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
                            .imePadding()
                            .verticalScroll(rememberScrollState()),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

                        DaySmilePicker(
                            smileChanged = { id: Int -> onIntent(DetailIntent.SmileResChanged(id)) }.toStable(),
                            smileImages = smileImages,
                            currentSmile = daySmileImage
                        )

                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

                        DayEditTextField(
                            modifier = Modifier
                                .fillMaxWidth(0.9F)
                                .weight(0.85F),
                            value = { dayTextEditable },
                            onValueChange = { value: String ->
                                onIntent(DetailIntent.DayDescChanged(value))
                            }.toStable(),
                        )
                        Spacer(modifier = Modifier.weight(0.15F))
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
                            modifier = Modifier.fillMaxSize(0.25F),
                            smileRes = daySmileImage
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

//                        GradientCard(
//                            modifier = Modifier
//                                .fillMaxSize(0.85F)
//                                .weight(1F),
//                            gradient = GradientDefaults.surface(),
//                            elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
//                        ) {
                        Text(
                            modifier = Modifier
                                .fillMaxHeight(0.8F)
                                .fillMaxWidth(0.9F),
                            text = dayText,
                            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp,
                        )
                    }
                }
            }
        }
    }
}