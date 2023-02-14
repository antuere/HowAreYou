package antuere.how_are_you.presentation.cats

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.LocalAppState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.GalleryProvider
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.cats.state.CatsSideEffect
import antuere.how_are_you.presentation.cats.ui_compose.CatsScreenState
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import org.orbitmvi.orbit.compose.collectAsState
import org.orbitmvi.orbit.compose.collectSideEffect
import timber.log.Timber

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CatsScreen(
    viewModel: CatsViewModel = hiltViewModel(),
) {
    Timber.i("MVI error test : enter in catsScreen screen")
    val appState = LocalAppState.current
    val context = LocalContext.current
    val viewState by viewModel.collectAsState()

    val writeExternalPermissionState = rememberPermissionState(
        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

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
                GalleryProvider.saveImage(
                    bitmap = sideEffect.bitmap,
                    context = context,
                    onSuccess = sideEffect.onSuccessSaved,
                    isHasPermission = writeExternalPermissionState.status.isGranted
                )
            }
            is CatsSideEffect.Snackbar -> {
                appState.showSnackbar(sideEffect.message.asString(context))
            }
        }
    }

    CatsScreenState(viewState = { viewState }, onIntent = { viewModel.onIntent(it) })
}
