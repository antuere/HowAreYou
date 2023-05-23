package antuere.how_are_you.presentation.screens.cats.ui_compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_theme.PlayfairDisplay
import antuere.how_are_you.presentation.screens.cats.state.CatsIntent
import antuere.how_are_you.presentation.screens.cats.state.CatsState
import antuere.how_are_you.presentation.screens.cats.ui_compose.components.CatImage
import antuere.how_are_you.presentation.screens.cats.ui_compose.components.ImageSourceSelectionDialog
import antuere.how_are_you.presentation.screens.cats.ui_compose.components.SourceImagesText
import antuere.how_are_you.util.extensions.fixedSize
import antuere.how_are_you.util.extensions.paddingTopBar
import kotlinx.collections.immutable.toImmutableList

@Composable
fun CatsScreenState(
    viewState: () -> CatsState,
    onIntent: (CatsIntent) -> Unit,
) {
    if (viewState().isShowSourceSelectionDialog) {
        ImageSourceSelectionDialog(
            onDismissRequest = { onIntent(CatsIntent.ChooseDialogClose) },
            onSourceSelected = { onIntent(CatsIntent.ImageSourceSelected(it)) },
            imageSources = viewState().allImageSources.toImmutableList(),
            currentValue = viewState().imageSource
        )
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewState().isLoading) {
            CircularProgressIndicator()
        } else {
            Spacer(modifier = Modifier.weight(1.5F))

            Text(
                text = stringResource(id = R.string.title_cats),
                fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.fixedSize,
                fontFamily = PlayfairDisplay
            )
            Spacer(modifier = Modifier.weight(1.5F))

            Row(
                modifier = Modifier.weight(10F),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CatImage(
                    modifier = Modifier
                        .weight(1F)
                        .padding(start = 8.dp, end = 4.dp),
                    url = viewState().imageSource.url1,
                    recompositionFlag = viewState().forceRecompositionFlag,
                    onLongClicked = { onIntent(CatsIntent.CatOnLongClicked(it)) },
                )
                CatImage(
                    modifier = Modifier
                        .weight(1F)
                        .padding(start = 4.dp, end = 8.dp),
                    url = viewState().imageSource.url2,
                    recompositionFlag = viewState().forceRecompositionFlag,
                    onLongClicked = { onIntent(CatsIntent.CatOnLongClicked(it)) },
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.weight(10f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                CatImage(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp, end = 4.dp),
                    url = viewState().imageSource.url3,
                    recompositionFlag = viewState().forceRecompositionFlag,
                    onLongClicked = { onIntent(CatsIntent.CatOnLongClicked(it)) },
                )
                CatImage(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 4.dp, end = 8.dp),
                    url = viewState().imageSource.url4,
                    recompositionFlag = viewState().forceRecompositionFlag,
                    onLongClicked = { onIntent(CatsIntent.CatOnLongClicked(it)) },
                )
            }
            Spacer(modifier = Modifier.weight(1.4F))

            Button(
                modifier = Modifier.fillMaxWidth(0.7F),
                onClick = { onIntent(CatsIntent.UpdateCatsClicked) }
            ) {
                Text(
                    text = stringResource(id = R.string.getCats),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
            Spacer(modifier = Modifier.weight(1.4F))

            SourceImagesText(
                onClick = { onIntent(CatsIntent.ImageSourceNameClicked(it)) },
                webSite = viewState().imageSource.webSite,
                sourceName = viewState().imageSource.name,
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_normal_1)))
        }
    }
}