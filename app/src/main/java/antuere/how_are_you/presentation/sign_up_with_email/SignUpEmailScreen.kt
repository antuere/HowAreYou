package antuere.how_are_you.presentation.sign_up_with_email

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.IconApp
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultButton
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.DefaultTextField
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.EmailTextField
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.PasswordTextField
import antuere.how_are_you.presentation.sign_up_with_email.state.SignUpEmailIntent
import antuere.how_are_you.presentation.sign_up_with_email.state.SignUpEmailSideEffect
import antuere.how_are_you.util.toStable
import antuere.how_are_you.util.paddingTopBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun SignUpEmailScreen(
    onNavigateSettings: () -> Unit,
    viewModel: SignUpEmailViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in signUppp screen")

    val context = LocalContext.current
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.sign_up,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            ),
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignUpEmailSideEffect.NavigateToSettings -> onNavigateSettings()
            is SignUpEmailSideEffect.Snackbar -> {
                appState.showSnackbar(sideEffect.message.asString(context))
            }
        }
    }

    if (viewState.isShowProgressIndicator) {
        FullScreenProgressIndicator()
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .paddingTopBar(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconApp(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_1)))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

            DefaultTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                value = viewState.nickName,
                onValueChange = { value: String ->
                    SignUpEmailIntent.NicknameChanged(value).run(viewModel::onIntent)
                }.toStable(),
                label = stringResource(id = R.string.nickname),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

            EmailTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                value = viewState.email,
                onValueChange = { value: String ->
                    SignUpEmailIntent.EmailChanged(value).run(viewModel::onIntent)
                }.toStable()
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

            PasswordTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                labelId = R.string.password,
                value = viewState.password,
                onValueChange = { value: String ->
                    SignUpEmailIntent.PasswordChanged(value).run(viewModel::onIntent)
                }.toStable()
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

            PasswordTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                labelId = R.string.confirm_password,
                value = viewState.confirmPassword,
                onValueChange = { value: String ->
                    SignUpEmailIntent.ConfirmPasswordChanged(value).run(viewModel::onIntent)
                }.toStable()
            )
            Spacer(modifier = Modifier.weight(1F))

            DefaultButton(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
                labelId = R.string.sign_up,
                onClick = {
                    SignUpEmailIntent.SignInBtnClicked(
                        email = viewState.email,
                        nickName = viewState.nickName,
                        password = viewState.password,
                        confirmPassword = viewState.confirmPassword
                    ).run(viewModel::onIntent)
                }.toStable()
            )
            Spacer(modifier = Modifier.weight(1F))
        }
    }
}