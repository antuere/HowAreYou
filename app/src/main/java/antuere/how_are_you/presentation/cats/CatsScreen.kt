package antuere.how_are_you.presentation.cats

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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.cats.ui_compose.DefaultGlideImage
import antuere.how_are_you.presentation.base.ui_theme.PlayfairDisplay
import antuere.how_are_you.presentation.cats.state.CatsIntent
import antuere.how_are_you.util.paddingTopBar
import org.orbitmvi.orbit.compose.collectAsState
import timber.log.Timber

@Composable
fun CatsScreen(
    viewModel: CatsViewModel = hiltViewModel()
) {
    Timber.i("MVI error test : enter in catsScreen screen")
    val appState = LocalAppState.current

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

    val viewState by viewModel.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.05F))

        Text(
            text = stringResource(id = R.string.title_cats),
            fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp,
            fontFamily = PlayfairDisplay
        )
        Spacer(modifier = Modifier.weight(0.05F))

        Row(modifier = Modifier.wrapContentHeight()) {
            DefaultGlideImage(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.dp),
                url = viewState.urlList[0],
                contentDescription = "cat"
            )
            DefaultGlideImage(
                modifier = Modifier
                    .weight(1F)
                    .padding(horizontal = 8.dp),
                url = viewState.urlList[1],
                contentDescription = "cat"
            )
        }
        Spacer(modifier = Modifier.weight(0.025F))

        Row(modifier = Modifier.wrapContentHeight()) {
            DefaultGlideImage(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.dp),
                url = viewState.urlList[2],
                contentDescription = "cat"
            )
            DefaultGlideImage(
                modifier = Modifier
                    .weight(1F)
                    .padding(horizontal = 8.dp),
                url = viewState.urlList[3],
                contentDescription = "cat"
            )
        }

        Spacer(modifier = Modifier.weight(0.05F))

        Button(
            modifier = Modifier.fillMaxWidth(0.7F),
            onClick = { CatsIntent.UpdateCatsClicked.run(viewModel::onIntent) }
        ) {
            Text(
                text = stringResource(id = R.string.getCats),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.weight(0.05F))
    }
}
