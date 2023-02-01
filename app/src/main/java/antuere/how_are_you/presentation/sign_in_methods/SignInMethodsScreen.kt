package antuere.how_are_you.presentation.sign_in_methods

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.ButtonWithIcon
import antuere.how_are_you.presentation.sign_in_methods.state.SignInMethodsIntent
import antuere.how_are_you.presentation.sign_in_methods.state.SignInMethodsSideEffect
import antuere.how_are_you.util.paddingTopBar
import antuere.how_are_you.util.toStable
import com.google.android.gms.auth.api.signin.GoogleSignIn
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun SignInMethodsScreen(
    onNavigateSignInEmail: () -> Unit,
    viewModel: SignInMethodsViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : composed in signinmethods screen")

    val appState = LocalAppState.current
    val context = LocalContext.current
    val viewState by viewModel.collectAsState()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                SignInMethodsIntent.GoogleAccAdded(task).run(viewModel::onIntent)
            }
        }

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.login_methods,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is SignInMethodsSideEffect.GoogleSignInDialog -> {
                launcher.launch(sideEffect.signInClient.signInIntent)
            }
            SignInMethodsSideEffect.NavigateToEmailMethod -> onNavigateSignInEmail()
            SignInMethodsSideEffect.NavigateUp -> appState.navigateUp()
            is SignInMethodsSideEffect.Snackbar -> {
                appState.showSnackbar(
                    sideEffect.message.asString(context)
                )
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ButtonWithIcon(
            modifier = Modifier.fillMaxWidth(0.7F),
            onClick = { SignInMethodsIntent.EmailMethodClicked.run(viewModel::onIntent) }.toStable(),
            labelId = viewState.emailMethod.nameId,
            iconId = viewState.emailMethod.iconId
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

        ButtonWithIcon(
            modifier = Modifier.fillMaxWidth(0.7F),
            onClick = { SignInMethodsIntent.GoogleMethodClicked.run(viewModel::onIntent) }.toStable(),
            labelId = viewState.googleMethod.nameId,
            iconId = viewState.googleMethod.iconId,
        )
    }
}