package antuere.how_are_you.presentation.screens.help_for_you.ui_compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.card.CardWithIcons
import antuere.how_are_you.presentation.screens.help_for_you.state.HelpForYouIntent
import antuere.how_are_you.presentation.screens.help_for_you.state.HelpForYouState
import antuere.how_are_you.util.extensions.paddingTopBar

@Composable
fun HelpForYouScreenState(
    viewState: () -> HelpForYouState,
    onIntent: (HelpForYouIntent) -> Unit,
) {
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
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
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
                    text = viewState().titleText.asString(),
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
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
            onClick = { onIntent(HelpForYouIntent.HelplinesCardClicked) },
            labelRes = viewState().helplinesCard.nameRes,
            leadingIconRes = viewState().helplinesCard.leadIconRes,
            trailingIconRes = viewState().helplinesCard.trailingIconRes
        )
        Spacer(modifier = Modifier.weight(0.05F))

        CardWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F),
            onClick = { onIntent(HelpForYouIntent.TelegramCardClicked) },
            labelRes = viewState().telegramCard.nameRes,
            leadingIconRes = viewState().telegramCard.leadIconRes
        )
        Spacer(modifier = Modifier.weight(0.05F))

        CardWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F),
            onClick = { onIntent(HelpForYouIntent.EmailCardClicked) },
            labelRes = viewState().emailCard.nameRes,
            leadingIconRes = viewState().emailCard.leadIconRes
        )
        Spacer(modifier = Modifier.weight(0.1F))
    }
}