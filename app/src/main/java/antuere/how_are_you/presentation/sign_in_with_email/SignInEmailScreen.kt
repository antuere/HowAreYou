package antuere.how_are_you.presentation.sign_in_with_email

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.IconApp
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultButton
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultTextButton
import antuere.how_are_you.presentation.base.ui_compose_components.placeholder.FullScreenProgressIndicator
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.EmailTextField
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.PasswordTextField
import antuere.how_are_you.presentation.sign_in_with_email.state.SignInEmailIntent
import antuere.how_are_you.presentation.sign_in_with_email.state.SignInEmailSideEffect
import antuere.how_are_you.util.paddingTopBar
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun SignInEmailScreen(
    onNavigateSettings: () -> Unit,
    onNavigateSignUp: () -> Unit,
    onNavigateResetPassword: () -> Unit,
    viewModel: SignInEmailViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in signIn screen")

    val context = LocalContext.current
    val appState = LocalAppState.current
    val viewState by viewModel.collectAsState()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.sign_in,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            SignInEmailSideEffect.NavigateToResetPassword -> onNavigateResetPassword()
            SignInEmailSideEffect.NavigateToSettings -> onNavigateSettings()
            SignInEmailSideEffect.NavigateToSignUp -> onNavigateSignUp()
            is SignInEmailSideEffect.Snackbar -> {
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
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            IconApp(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_1)))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

            EmailTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                value = viewState.email,
                onValueChange = { SignInEmailIntent.EmailChanged(it).run(viewModel::onIntent) })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

            PasswordTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                labelId = R.string.password,
                value = viewState.password,
                onValueChange = { SignInEmailIntent.PasswordChanged(it).run(viewModel::onIntent) })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

            DefaultTextButton(
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_normal_3))
                    .align(Alignment.End),
                labelId = R.string.reset_password_hint,
                onClick = { SignInEmailIntent.ResetPassBtnClicked.run(viewModel::onIntent) })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_11)))

            DefaultButton(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                labelId = R.string.sign_in,
                onClick = {
                    SignInEmailIntent.SignInBtnClicked(
                        email = viewState.email,
                        password = viewState.password
                    ).run(viewModel::onIntent)
                }
            )
            Spacer(modifier = Modifier.weight(1F))

            DefaultTextButton(
                modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
                labelId = R.string.don_have_acc,
                onClick = { SignInEmailIntent.SignUpBtnClicked.run(viewModel::onIntent) }
            )
            Spacer(modifier = Modifier.weight(1F))
        }
    }
}
