package com.example.zeroapp.presentation.sign_in_methods

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.sign_in_methods.ui_compose.ButtonWithIcon
import com.example.zeroapp.presentation.sign_in_with_email.SignInState
import com.example.zeroapp.util.ShowSnackBar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignInMethodsScreen(
    modifier: Modifier = Modifier,
    onComposing: (AppBarState, Boolean) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateSignInEmail: () -> Unit,
    signInMethodsViewModel: SignInMethodsViewModel = hiltViewModel(),
    signInClient: GoogleSignInClient,
) {
    val signInState by signInMethodsViewModel.signInState.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                signInMethodsViewModel.handleResult(task)
            }
        }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                titleId = R.string.login_methods,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() }
            ),
            false
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(snackbarHostState) { data ->
                Snackbar(
                    snackbarData = data,
                    containerColor = MaterialTheme.colorScheme.onPrimary,
                    contentColor = MaterialTheme.colorScheme.onSecondary
                )
            }
        }
    ) { it ->
        signInState?.let { state ->
            when (state) {
                is SignInMethodsState.UserAuthorized -> {
                    onNavigateUp()
                }
                is SignInMethodsState.Error -> {
                    snackbarHostState.ShowSnackBar(message = state.message)
                }
            }
            signInMethodsViewModel.nullifyState()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
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
}