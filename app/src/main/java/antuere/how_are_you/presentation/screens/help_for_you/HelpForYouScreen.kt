package antuere.how_are_you.presentation.screens.help_for_you

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.domain.util.Constants
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.help_for_you.state.HelpForYouSideEffect
import antuere.how_are_you.presentation.screens.help_for_you.ui_compose.HelpForYouScreenState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun HelpForYouScreen(
    onNavigateToHelplines: () -> Unit,
    viewModel: HelpForYouViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val viewState by viewModel.collectAsState()

    appState.DisableBackBtnWhileTransitionAnimate()
    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.help_for_you,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            HelpForYouSideEffect.NavigateToHelplines -> onNavigateToHelplines()
            HelpForYouSideEffect.NavigateToEmailClient -> {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:${Constants.HELP_EMAIL}")
                }
                val title = context.getString(R.string.choose_title)
                try {
                    context.startActivity(Intent.createChooser(intent, title))
                } catch (e: ActivityNotFoundException) {
                    appState.showSnackbar(context.getString(R.string.email_client_not_found))
                }
            }
            HelpForYouSideEffect.NavigateToTelegram -> {
                uriHandler.openUri(Constants.HELP_TELEGRAM)
            }
        }
    }

    HelpForYouScreenState(viewState = { viewState }, onIntent = { viewModel.onIntent(it) })
}
