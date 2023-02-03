package antuere.how_are_you.presentation.cats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.cats.ui_compose.CatImage
import antuere.how_are_you.presentation.base.ui_theme.PlayfairDisplay
import antuere.how_are_you.presentation.cats.state.CatsIntent
import antuere.how_are_you.util.paddingTopBar
import antuere.how_are_you.util.toStable
import org.orbitmvi.orbit.compose.collectAsState
import timber.log.Timber

@Composable
fun CatsScreen(
    viewModel: CatsViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in catsScreen screen")
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.cats,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(2F))

        Text(
            text = stringResource(id = R.string.title_cats),
            fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp,
            fontFamily = PlayfairDisplay
        )
        Spacer(modifier = Modifier.weight(2F))

        Row(
            modifier = Modifier.weight(10F),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CatImage(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 6.dp, end = 3.dp),
                url = viewState.urlList[0],
            )
            CatImage(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 3.dp, end = 6.dp),
                url = viewState.urlList[1],
            )
        }
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.weight(10f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CatImage(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 6.dp, end = 3.dp),
                url = viewState.urlList[2],
            )
            CatImage(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 3.dp, end = 6.dp),
                url = viewState.urlList[3],
            )
        }
        Spacer(modifier = Modifier.weight(2F))

        Button(
            modifier = Modifier.fillMaxWidth(0.7F),
            onClick = { CatsIntent.UpdateCatsClicked.run(viewModel::onIntent) }.toStable()
        ) {
            Text(
                text = stringResource(id = R.string.getCats),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.weight(2F))
    }
}
