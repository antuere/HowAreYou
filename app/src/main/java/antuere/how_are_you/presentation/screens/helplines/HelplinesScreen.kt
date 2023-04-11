package antuere.how_are_you.presentation.screens.helplines

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.helplines.state.HelplinesSideEffect
import antuere.how_are_you.presentation.screens.helplines.ui_compose.HelplinesScreenState
import antuere.how_are_you.util.extensions.animateScrollAndCentralize
import antuere.how_are_you.util.extensions.isScrollInInitialState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@Composable
fun HelplinesScreen(
    viewModel: HelplinesViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in helplines screen, view model id ${viewModel.toString()}")
    val appState = LocalAppState.current
    val context = LocalContext.current
    val uriHandler = LocalUriHandler.current
    val lazyListState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    val viewState by viewModel.collectAsState()

    val isShowShadowAboveList by remember {
        derivedStateOf {
            !lazyListState.isScrollInInitialState()
        }
    }

    appState.DisableBackBtnWhileTransitionAnimate()

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is HelplinesSideEffect.NavigateToDialNumber -> {
                val phoneUri = Uri.parse("tel:" + sideEffect.phoneNumber)
                val intent = Intent(Intent.ACTION_DIAL, phoneUri)
                try {
                    context.startActivity(intent)
                } catch (s: SecurityException) {
                    appState.showSnackbar(s.message ?: "Security exception")
                }
            }
            is HelplinesSideEffect.NavigateToWebsite -> {
                uriHandler.openUri(sideEffect.website)
            }
            is HelplinesSideEffect.ScrollToCenterItem -> {
                scope.launch {
                    delay(200)
                    lazyListState.animateScrollAndCentralize(sideEffect.itemIndex, this)
                }
            }
            HelplinesSideEffect.ScrollToTop -> {
                scope.launch {
                    lazyListState.animateScrollToItem(0)
                }
            }
        }
    }

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.helplines,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    HelplinesScreenState(
        lazyListState = { lazyListState },
        viewState = { viewState },
        onIntent = { viewModel.onIntent(it) },
        isShowShadow = { isShowShadowAboveList }
    )
}