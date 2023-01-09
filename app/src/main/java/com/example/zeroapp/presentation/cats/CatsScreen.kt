package com.example.zeroapp.presentation.cats

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.cats.ui_compose.DefaultGlideImage
import com.example.zeroapp.presentation.base.ui_theme.PlayfairDisplay

@Composable
fun CatsScreen(
    updateAppBar: (AppBarState) -> Unit,
    onNavigateUp: () -> Unit,
    catsViewModel: CatsViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        updateAppBar(
            AppBarState(
                titleId = R.string.cats,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() },
                isVisibleBottomBar = false
            ),
        )
    }

    val urlList by catsViewModel.urlList.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize(),
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
                url = urlList[0],
                contentDescription = "cat"
            )
            DefaultGlideImage(
                modifier = Modifier
                    .weight(1F)
                    .padding(horizontal = 8.dp),
                url = urlList[1],
                contentDescription = "cat"
            )
        }
        Spacer(modifier = Modifier.weight(0.025F))

        Row(modifier = Modifier.wrapContentHeight()) {
            DefaultGlideImage(
                modifier = Modifier
                    .weight(1F)
                    .padding(start = 8.dp),
                url = urlList[2],
                contentDescription = "cat"
            )
            DefaultGlideImage(
                modifier = Modifier
                    .weight(1F)
                    .padding(horizontal = 8.dp),
                url = urlList[3],
                contentDescription = "cat"
            )
        }

        Spacer(modifier = Modifier.weight(0.05F))

        Button(
            modifier = Modifier.fillMaxWidth(0.7F),
            onClick = { catsViewModel.onClickUpdateCats() }) {
            Text(
                text = stringResource(id = R.string.getCats),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        Spacer(modifier = Modifier.weight(0.05F))
    }
}
