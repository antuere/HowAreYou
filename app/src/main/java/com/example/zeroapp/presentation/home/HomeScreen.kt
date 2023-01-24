package com.example.zeroapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.home.ui_compose.CardWithQuote
import com.example.zeroapp.presentation.base.ui_compose_components.card.CardDefault
import com.example.zeroapp.presentation.base.ui_compose_components.card.CardWithOnClick
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.base.ui_theme.TealMain
import com.example.zeroapp.util.paddingForBotAndTopBar

@Composable
fun HomeScreen(
    updateAppBar: (AppBarState) -> Unit,
    dismissSnackbar: () -> Unit,
    showSnackbar: (String) -> Unit,
    onNavigateToMentalTips: () -> Unit,
    onNavigateToHelpForYou: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToCats: () -> Unit,
    onNavigateToDetail: (Long) -> Unit,
    onNavigateToAddDay: () -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val uiDialog by homeViewModel.uiDialog.collectAsState()
    val dayQuote by homeViewModel.dayQuote.collectAsState()
    val wishText by homeViewModel.wishText.collectAsState()
    val isShowMessage by homeViewModel.isShowMessage.collectAsState()
    val fabBtnState by homeViewModel.fabButtonState.collectAsState()

    val warningMessage = stringResource(id = R.string.snack_bar_warning_negative)

    uiDialog?.let {
        Dialog(dialog = it)
    }

    LaunchedEffect(true) {
        updateAppBar(
            AppBarState(
                titleId = R.string.home,
                isVisibleBottomBar = true
            ),
        )
        dismissSnackbar()
    }

    LaunchedEffect(isShowMessage) {
        if (isShowMessage) {
            showSnackbar(warningMessage)
            homeViewModel.resetMessage()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_normal_0))
            .paddingForBotAndTopBar(),
        verticalArrangement = Arrangement.Top
    ) {
        CardWithQuote(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.3F),
            titleText = stringResource(
                id = R.string.quotes_title
            ),
            quoteText = dayQuote?.text ?: "Test quote",
            quiteAuthor = dayQuote?.author ?: "Test author"
        )

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.2F),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardWithOnClick(
                onClick = onNavigateToMentalTips,
                cardModifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_small_1),
                        end = dimensionResource(id = R.dimen.padding_small_0)
                    )
                    .weight(0.5F),
                titleText = stringResource(id = R.string.mental_tips)
            )
            CardWithOnClick(
                onClick = onNavigateToHelpForYou,
                cardModifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_small_1),
                        start = dimensionResource(id = R.dimen.padding_small_0)
                    )
                    .weight(0.5F),
                titleText = stringResource(id = R.string.help_for_you)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxSize()
                .weight(0.2F),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CardWithOnClick(
                onClick = onNavigateToFavorites,
                cardModifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_small_1),
                        end = dimensionResource(id = R.dimen.padding_small_0)
                    )
                    .weight(0.5F),
                titleText = stringResource(id = R.string.favorites),
            )
            CardWithOnClick(
                onClick = onNavigateToCats,
                cardModifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_small_1),
                        start = dimensionResource(id = R.dimen.padding_small_0)
                    )
                    .weight(0.5F),
                titleText = stringResource(id = R.string.cats)
            )
        }

        CardDefault(
            cardModifier = Modifier
                .fillMaxSize()
                .weight(0.3F)
                .padding(top = dimensionResource(id = R.dimen.padding_small_1)),
            textModifier = Modifier.padding(
                top = dimensionResource(id = R.dimen.padding_normal_1),
                start = dimensionResource(id = R.dimen.padding_normal_1),
                end = dimensionResource(id = R.dimen.padding_normal_1)
            ),
            titleText = wishText.asString(),
            textAlignment = Alignment.TopStart
        ) {
            Spacer(modifier = Modifier.weight(1F))

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(
                        bottom = dimensionResource(id = R.dimen.padding_normal_1),
                        start = dimensionResource(id = R.dimen.padding_normal_1),
                        end = dimensionResource(id = R.dimen.padding_normal_1)
                    ), onClick = {
                    when (fabBtnState) {
                        is FabButtonState.Add -> {
                            onNavigateToAddDay()
                        }
                        is FabButtonState.Smile -> {
                            onNavigateToDetail((fabBtnState as FabButtonState.Smile).dayId)
                        }
                    }

                }, containerColor = TealMain
            ) {
                when (fabBtnState) {
                    is FabButtonState.Add -> {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_plus),
                            modifier = Modifier.size(24.dp),
                            contentDescription = null
                        )
                    }
                    is FabButtonState.Smile -> {
                        Icon(
                            painter = painterResource(id = fabBtnState.image),
                            modifier = Modifier.size(24.dp),
                            contentDescription = null
                        )
                    }
                }
            }
        }

    }
}
