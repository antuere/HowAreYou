package antuere.how_are_you.presentation.help_for_you

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.card.CardWithIcons
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.help_for_you.state.HelpForYouIntent
import antuere.how_are_you.presentation.help_for_you.state.HelpForYouSideEffect
import antuere.how_are_you.util.paddingTopBar
import antuere.how_are_you.util.toStable
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun HelpForYouScreen(
    onNavigateToHelplines: () -> Unit,
    viewModel: HelpForYouViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in help for u screen")

    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.help_for_you,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            HelpForYouSideEffect.NavigateToHelplines -> onNavigateToHelplines()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_normal_0))
            .paddingTopBar(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4F)
                .align(Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_1)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = viewState.titleText.asString(),
                    textAlign = TextAlign.Center,
                    fontSize = dimensionResource(id = R.dimen.textSize_normal_1).value.sp
                )
            }
        }
        Spacer(modifier = Modifier.weight(0.1F))

//        TODO добавить описание к иконкам
        CardWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F),
            onClick = { HelpForYouIntent.HelplinesCardClicked.run(viewModel::onIntent) }.toStable(),
            labelRes = viewState.helplinesCard.nameRes,
            leadingIconRes = viewState.helplinesCard.leadIconRes,
            trailingIconRes = viewState.helplinesCard.trailingIconRes
        )
        Spacer(modifier = Modifier.weight(0.05F))

        CardWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F),
            onClick = { HelpForYouIntent.TelegramCardClicked.run(viewModel::onIntent) }.toStable(),
            labelRes = viewState.telegramCard.nameRes,
            leadingIconRes = viewState.telegramCard.leadIconRes
        )
        Spacer(modifier = Modifier.weight(0.05F))

        CardWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F),
            onClick = { HelpForYouIntent.EmailCardClicked.run(viewModel::onIntent) }.toStable(),
            labelRes = viewState.emailCard.nameRes,
            leadingIconRes = viewState.emailCard.leadIconRes
        )
        Spacer(modifier = Modifier.weight(0.1F))
    }
}
