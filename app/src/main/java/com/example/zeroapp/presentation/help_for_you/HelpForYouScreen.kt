package com.example.zeroapp.presentation.help_for_you

import android.location.Geocoder
import android.location.Location
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.card.CardWithIcons
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState

@Composable
fun HelpForYouScreen(
    updateAppBar: (AppBarState) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateToHelplines : () -> Unit,
    helpForYouViewModel: HelpForYouViewModel = hiltViewModel()
) {
    LaunchedEffect(true) {
        updateAppBar(
            AppBarState(
                titleId = R.string.help_for_you,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() },
                isVisibleBottomBar = false
            ),
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.padding_normal_0)),
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
                    text = stringResource(id = R.string.help_for_you_title_card),
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
            onClick = onNavigateToHelplines,
            labelRes = R.string.helplines,
            leadingIconRes = R.drawable.ic_hotlines,
            trailingIconRes = R.drawable.ic_round_chevron_right
        )
        Spacer(modifier = Modifier.weight(0.05F))

        CardWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F),
            onClick = {},
            labelRes = R.string.telegram_chat,
            leadingIconRes = R.drawable.ic_telegram
        )
        Spacer(modifier = Modifier.weight(0.05F))

        CardWithIcons(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.2F),
            onClick = {},
            labelRes = R.string.email_chat,
            leadingIconRes = R.drawable.ic_email
        )

        Spacer(modifier = Modifier.weight(0.1F))
    }
}
