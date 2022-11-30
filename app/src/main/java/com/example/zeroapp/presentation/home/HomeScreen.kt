package com.example.zeroapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.home.ui_compose.CardWithQuote
import com.example.zeroapp.presentation.base.ui_compose_components.CardDefault
import com.example.zeroapp.presentation.base.ui_compose_components.CardWithOnClick
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.Dialog
import com.example.zeroapp.presentation.base.ui_theme.TealMain

@Composable
fun HomeScreen(
//    navController: NavController,
    modifier: Modifier = Modifier, homeViewModel: HomeViewModel = hiltViewModel()
) {

    val uiDialog by homeViewModel.uiDialog.collectAsState()
    val dayQuote by homeViewModel.dayQuote.collectAsState()
    val wishText by homeViewModel.wishText.collectAsState()
    val isShowSnackBar by homeViewModel.isShowSnackBar.collectAsState()
    val fabBtnState by homeViewModel.fabButtonState.collectAsState()

    val middleBtnNameId = listOf(
        R.string.mental_tips, R.string.help_for_you, R.string.favorites, R.string.cats
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_normal_0))
    ) {
        uiDialog?.let {
            Dialog(dialog = it)
        }

        Column(verticalArrangement = Arrangement.Top) {
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

            for (i in 0..2 step (2)) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(0.2F),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    CardWithOnClick(
                        cardModifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                end = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = middleBtnNameId[i])
                    )
                    CardWithOnClick(
                        cardModifier = Modifier
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_small_1),
                                start = dimensionResource(id = R.dimen.padding_small_0)
                            )
                            .weight(0.5F),
                        titleText = stringResource(id = middleBtnNameId[i + 1])
                    )
                }
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
                titleText = wishText,
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
                        ), onClick = { homeViewModel.testDialog() }, containerColor = TealMain
                ) {
                    when (fabBtnState) {
                        is FabButtonState.Add -> {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_plus),
                                contentDescription = null
                            )
                        }
                        is FabButtonState.Smile -> {
                            Icon(
                                painter = painterResource(id = fabBtnState.image),
                                contentDescription = null
                            )
                        }
                    }
                }
            }

        }


    }

}