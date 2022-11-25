package com.example.zeroapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.CardWithQuote
import com.example.zeroapp.presentation.base.ui_compose_components.CardWithTitleCenter
import com.example.zeroapp.presentation.base.ui_dialog.UIDialogListener

@Composable
fun HomeScreen(
//    navController: NavController,
    modifier: Modifier = Modifier,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val dialogListener = UIDialogListener(LocalContext.current, homeViewModel)
    val dayQuote by homeViewModel.dayQuote.collectAsState()
    val wishText by homeViewModel.wishText.collectAsState()
    val isShowSnackBar by homeViewModel.isShowSnackBar.collectAsState()
    val fabBtnState by homeViewModel.fabButtonState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_normal_0))
//            .background(Color.Red)
    ) {
        Column(verticalArrangement = Arrangement.Top) {
            CardWithQuote(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3F),
                titleText = stringResource(
                    id = R.string.quotes_title
                ),
                quoteText = dayQuote?.text ?: "Test quote",
                quiteAuthor = dayQuote?.author ?: "Test author"
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3F),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardWithTitleCenter(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_normal_0),
                            end = dimensionResource(id = R.dimen.padding_small_1)
                        )
                        .weight(0.5F),
                    titleText = "Mental tips"
                )
                CardWithTitleCenter(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_normal_0),
                            start = dimensionResource(id = R.dimen.padding_small_1)
                        )
                        .weight(0.5F),
                    titleText = "Help for you"
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4F),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CardWithTitleCenter(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_normal_0),
                            end = dimensionResource(id = R.dimen.padding_small_1)
                        )
                        .weight(0.5F),
                    titleText = stringResource(id = R.string.favorites)
                )
                CardWithTitleCenter(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_normal_0),
                            start = dimensionResource(id = R.dimen.padding_small_1)
                        )
                        .weight(0.5F),
                    titleText = stringResource(id = R.string.cats)
                )
            }
        }
    }
}