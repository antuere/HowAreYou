package antuere.how_are_you.presentation.add_day

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.add_day.state.AddDayIntent
import antuere.how_are_you.presentation.add_day.state.AddDaySideEffect
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.DefaultTextField
import antuere.how_are_you.presentation.base.ui_theme.PlayfairDisplay
import antuere.how_are_you.util.paddingTopBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun AddDayScreen(
    viewModel: AddDayViewModel = hiltViewModel()
) {
    Timber.i("MVI error test : enter in add day screen")
    val appState = LocalAppState.current

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.today,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }
    val viewState by viewModel.collectAsState()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is AddDaySideEffect.NavigateUp -> appState.navigateUp()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.15F))
        Text(
            text = stringResource(id = R.string.how_are_you_today),
            fontSize = dimensionResource(id = R.dimen.textSize_big_1).value.sp,
            fontFamily = PlayfairDisplay
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_10)))

        DefaultTextField(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                .fillMaxWidth(),
            label = stringResource(id = R.string.desc_you_day),
            value = viewState.dayDesc,
            onValueChange = { AddDayIntent.DayDescChanged(value = it).run(viewModel::onIntent) },
            maxLength = 100,
        )
        Spacer(modifier = Modifier.weight(0.25F))

        Row(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
        ) {
            viewState.smileImages.forEach { imageRes ->
                IconButton(
                    onClick = {
                        AddDayIntent.SmileClicked(
                            imageResId = imageRes,
                            descDay = viewState.dayDesc
                        ).run(viewModel::onIntent)
                        AddDayIntent.DayDescChanged(value = "").run(viewModel::onIntent)
                    }) {
                    Icon(painter = painterResource(id = imageRes), contentDescription = null)
                }
            }
        }
        Spacer(modifier = Modifier.weight(0.25F))
    }
}