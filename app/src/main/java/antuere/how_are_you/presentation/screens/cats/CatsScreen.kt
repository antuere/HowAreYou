package antuere.how_are_you.presentation.screens.cats

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.data.util.GalleryProvider
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.screens.cats.state.CatsIntent
import antuere.how_are_you.presentation.screens.cats.state.CatsSideEffect
import antuere.how_are_you.presentation.screens.cats.ui_compose.CatsScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect

@Composable
fun CatsScreen(
    viewModel: CatsViewModel = hiltViewModel(),
) {
    val appState = LocalAppState.current
    val context = LocalContext.current
    val hapticFeedback = LocalHapticFeedback.current
    val scope = rememberCoroutineScope()
    val viewState by viewModel.collectAsState()

    val writeExternalPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            CatsIntent.WriteExternalPermissionCalled(isGranted).run(viewModel::onIntent)
        }
    )

    appState.DisableBackBtnWhileTransitionAnimate()

    LaunchedEffect(true) {
        appState.updateAppBar(
            AppBarState(
                titleId = R.string.cats,
                navigationIcon = Icons.Filled.ArrowBack,
                onClickNavigationBtn = appState::navigateUp,
                isVisibleBottomBar = false
            )
        )
    }

    viewModel.collectSideEffect { sideEffect ->
        when (sideEffect) {
            is CatsSideEffect.SaveImageToGallery -> {
                scope.launch(Dispatchers.IO) {
                    GalleryProvider.saveImage(
                        bitmap = sideEffect.bitmap,
                        context = context,
                        onSuccess = sideEffect.onSuccessSaved,
                    )
                }
            }
            is CatsSideEffect.Snackbar -> {
                appState.showSnackbar(
                    message = sideEffect.message.asString(context),
                    duration = 1000
                )
            }
            CatsSideEffect.RequestPermission -> {
                writeExternalPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
            CatsSideEffect.Vibration -> {
                appState.vibratePhone(hapticFeedback)
            }
        }
    }

    CatsScreenState(viewState = { viewState }, onIntent = { viewModel.onIntent(it) })
}
