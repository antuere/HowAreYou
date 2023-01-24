package com.example.zeroapp.presentation.sign_in_methods

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.IconApp
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.buttons.ButtonWithIcon
import com.example.zeroapp.util.paddingTopBar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient

@Composable
fun SignInMethodsScreen(
    updateAppBar: (AppBarState) -> Unit,
    showSnackbar: (String) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateSignInEmail: () -> Unit,
    signInClient: GoogleSignInClient,
    signInMethodsViewModel: SignInMethodsViewModel = hiltViewModel()
) {
    val signInState by signInMethodsViewModel.signInState.collectAsState()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                signInMethodsViewModel.handleResult(task)
            }
        }

    LaunchedEffect(true) {
        updateAppBar(
            AppBarState(
                titleId = R.string.login_methods,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() },
                isVisibleBottomBar = false
            )
        )
    }

    LaunchedEffect(signInState) {
        signInState?.let { state ->
            when (state) {
                is SignInMethodsState.UserAuthorized -> {
                    onNavigateUp()
                }
                is SignInMethodsState.Error -> {
                    showSnackbar(state.message)
                }
            }
            signInMethodsViewModel.nullifyState()
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
            onClick = { onNavigateSignInEmail() },
            labelId = R.string.login_email,
            iconId = R.drawable.ic_email
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

        ButtonWithIcon(
            modifier = Modifier.fillMaxWidth(0.7F),
            onClick = { launcher.launch(signInClient.signInIntent) },
            labelId = R.string.login_google,
            iconId = R.drawable.ic_google
        )
    }
}