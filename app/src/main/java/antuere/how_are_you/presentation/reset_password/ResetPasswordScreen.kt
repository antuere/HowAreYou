package antuere.how_are_you.presentation.reset_password

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultButton
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.EmailTextField
import antuere.how_are_you.presentation.reset_password.state.ResetPasswordIntent
import antuere.how_are_you.presentation.reset_password.state.ResetPasswordSideEffect
import antuere.how_are_you.util.extensions.paddingTopBar
import antuere.how_are_you.util.extensions.toStable
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun ResetPasswordScreen(
    viewModel: ResetPasswordViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in reset password screen")
    val context = LocalContext.current
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.reset_password,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            ResetPasswordSideEffect.NavigateUp -> appState.navigateUp()
            is ResetPasswordSideEffect.Snackbar -> appState.showSnackbar(
                sideEffect.message.asString(context)
            )
        }
    }

    if (viewState.isShowProgressIndicator) {
        FullScreenProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingTopBar(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))
            Text(text = stringResource(id = R.string.reset_password_plug), fontSize = 18.sp)

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_8)))
            EmailTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                value = viewState.email,
                onValueChange = { value: String ->
                    ResetPasswordIntent.EmailChanged(value).run(viewModel::onIntent)
                }.toStable()
            )

            Spacer(modifier = Modifier.weight(1F))

            DefaultButton(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
                labelId = R.string.reset_password,
                onClick = {
                    ResetPasswordIntent.ResetBtnClicked(userEmail = viewState.email)
                        .run(viewModel::onIntent)
                }.toStable()
            )
        }
    }
}
