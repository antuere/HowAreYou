package antuere.how_are_you.presentation.screens.cats.ui_compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import antuere.domain.dto.ImageSource
import antuere.how_are_you.R
import antuere.how_are_you.util.extensions.fixedSize
import kotlinx.collections.immutable.ImmutableList
import kotlin.math.max

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSourceSelectionDialog(
    modifier: Modifier = Modifier,
    onDismissRequest: () -> Unit,
    onSourceSelected: (ImageSource) -> Unit,
    imageSources: ImmutableList<ImageSource>,
    currentValue: ImageSource
) {
    var selectedSource by remember {
        mutableStateOf(currentValue)
    }

    AlertDialog(
        onDismissRequest = onDismissRequest,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.background,
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(
                modifier = modifier
                    .padding(dimensionResource(id = R.dimen.padding_normal_2)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_image_source),
                    contentDescription = "Image selector",
                    tint = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

                Text(
                    text = stringResource(R.string.animal_choose_source),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineSmall.copy(fontSize = 24f.fixedSize)
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

                Text(
                    stringResource(R.string.animal_choose_desc),
                    style = MaterialTheme.typography.bodyMedium
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_normal_2)))

                ImageSourcesSelector(
                    value = selectedSource,
                    imageSources = imageSources,
                    onImageSourceChanged = { selectedSource = it }
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_normal_2)))

                Box(modifier = Modifier.align(Alignment.End)) {
                    DefaultDialogFlowRow {
                        TextButton(onClick = onDismissRequest) {
                            Text(
                                stringResource(id = R.string.animal_choose_neg),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                        TextButton(onClick = { onSourceSelected(selectedSource) }) {
                            Text(
                                stringResource(id = R.string.animal_choose_pos),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

            }
        }
    }
}

//Implementation by androidx.compose.material3
@Composable
fun DefaultDialogFlowRow(
    mainAxisSpacing: Dp = 8.dp,
    crossAxisSpacing: Dp = 12.dp,
    content: @Composable () -> Unit
) {
    Layout(content) { measurables, constraints ->
        val sequences = mutableListOf<List<Placeable>>()
        val crossAxisSizes = mutableListOf<Int>()
        val crossAxisPositions = mutableListOf<Int>()

        var mainAxisSpace = 0
        var crossAxisSpace = 0

        val currentSequence = mutableListOf<Placeable>()
        var currentMainAxisSize = 0
        var currentCrossAxisSize = 0

        fun canAddToCurrentSequence(placeable: Placeable) =
            currentSequence.isEmpty() || currentMainAxisSize + mainAxisSpacing.roundToPx() +
                    placeable.width <= constraints.maxWidth

        fun startNewSequence() {
            if (sequences.isNotEmpty()) {
                crossAxisSpace += crossAxisSpacing.roundToPx()
            }
            sequences += currentSequence.toList()
            crossAxisSizes += currentCrossAxisSize
            crossAxisPositions += crossAxisSpace

            crossAxisSpace += currentCrossAxisSize
            mainAxisSpace = max(mainAxisSpace, currentMainAxisSize)

            currentSequence.clear()
            currentMainAxisSize = 0
            currentCrossAxisSize = 0
        }

        for (measurable in measurables) {
            val placeable = measurable.measure(constraints)

            if (!canAddToCurrentSequence(placeable)) startNewSequence()

            if (currentSequence.isNotEmpty()) {
                currentMainAxisSize += mainAxisSpacing.roundToPx()
            }
            currentSequence.add(placeable)
            currentMainAxisSize += placeable.width
            currentCrossAxisSize = max(currentCrossAxisSize, placeable.height)
        }

        if (currentSequence.isNotEmpty()) startNewSequence()

        val mainAxisLayoutSize = max(mainAxisSpace, constraints.minWidth)

        val crossAxisLayoutSize = max(crossAxisSpace, constraints.minHeight)

        layout(mainAxisLayoutSize, crossAxisLayoutSize) {
            sequences.forEachIndexed { i, placeables ->
                val childrenMainAxisSizes = IntArray(placeables.size) { j ->
                    placeables[j].width +
                            if (j < placeables.lastIndex) mainAxisSpacing.roundToPx() else 0
                }
                val arrangement = Arrangement.End
                val mainAxisPositions = IntArray(childrenMainAxisSizes.size) { 0 }
                with(arrangement) {
                    arrange(
                        mainAxisLayoutSize, childrenMainAxisSizes,
                        layoutDirection, mainAxisPositions
                    )
                }
                placeables.forEachIndexed { j, placeable ->
                    placeable.place(
                        x = mainAxisPositions[j],
                        y = crossAxisPositions[i]
                    )
                }
            }
        }
    }
}