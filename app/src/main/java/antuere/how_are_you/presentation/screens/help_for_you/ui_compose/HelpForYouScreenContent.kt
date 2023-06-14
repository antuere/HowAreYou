package antuere.how_are_you.presentation.screens.help_for_you.ui_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.card.CardWithIcons
import antuere.how_are_you.presentation.base.ui_compose_components.card.GradientCard
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults
import antuere.how_are_you.presentation.screens.help_for_you.state.HelpForYouIntent
import antuere.how_are_you.presentation.screens.help_for_you.state.HelpForYouState
import antuere.how_are_you.util.extensions.fixedSize
import antuere.how_are_you.util.extensions.paddingTopBar
import eu.wewox.textflow.TextFlow

@Composable
fun HelpForYouScreenContent(
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
        GradientCard(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.4F)
                .align(Alignment.CenterHorizontally),
            shape = MaterialTheme.shapes.large,
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            gradient = GradientDefaults.primaryTriple()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(dimensionResource(id = R.dimen.padding_normal_0))
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextFlow(
                    text = viewState().titleText.asString(),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = dimensionResource(id = R.dimen.textSize_normal_1).value.fixedSize,
                    style = MaterialTheme.typography.titleMedium
                ) {
                    Image(
                        modifier = Modifier
                            .fillMaxWidth(0.25F)
                            .padding(end = 8.dp),
                        painter = painterResource(id = R.drawable.help_hand),
                        contentDescription = "Text flow",
                        contentScale = ContentScale.Crop
                    )
                }
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