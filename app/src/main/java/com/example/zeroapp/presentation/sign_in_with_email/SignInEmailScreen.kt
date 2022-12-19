package com.example.zeroapp.presentation.sign_in_with_email

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.IconApp
import com.example.zeroapp.presentation.base.ui_compose_components.text_field.EmailTextField
import com.example.zeroapp.presentation.base.ui_compose_components.text_field.PasswordTextField
import com.example.zeroapp.presentation.sign_in_with_email.ui_compose.ForgotPassBtn
import com.example.zeroapp.presentation.sign_in_with_email.ui_compose.SignInButton
import com.example.zeroapp.presentation.sign_in_with_email.ui_compose.SignUpHintButton
import com.example.zeroapp.util.ShowSnackBar

@Composable
fun SignInEmailScreen(
    modifier: Modifier = Modifier,
    onComposing: (AppBarState, Boolean) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateSettings: () -> Unit,
    onNavigateSignUp: () -> Unit,
    onNavigateResetPassword: () -> Unit,
    snackbarHostState: SnackbarHostState,
    signInEmailViewModel: SignInEmailViewModel = hiltViewModel()
) {
    val isShowLoginProgressIndicator by signInEmailViewModel.isShowLoginProgressIndicator.collectAsState()
    val signInState by signInEmailViewModel.signInState.collectAsState()

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                titleId = R.string.sign_in,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() }
            ),
            false
        )
    }

    //    TODO подумать как сделать элегантней ч2
    signInState?.let { state ->
        when (state) {
            is SignInState.Successful -> {
                onNavigateSettings()
                signInEmailViewModel.nullifyState()
                signInEmailViewModel.resetIsShowLoginProgressIndicator(true)
            }
            is SignInState.EmptyFields -> {
                snackbarHostState.ShowSnackBar(message = stringResource(id = R.string.empty_fields))
                signInEmailViewModel.nullifyState(true)
            }

            is SignInState.ErrorFromFireBase -> {
                snackbarHostState.ShowSnackBar(message = state.message)
                signInEmailViewModel.nullifyState(true)
                signInEmailViewModel.resetIsShowLoginProgressIndicator()
            }
        }

    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconApp(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_1)))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

        if (!isShowLoginProgressIndicator) {
            EmailTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                value = userEmail,
                onValueChange = { userEmail = it })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

            PasswordTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                labelId = R.string.password,
                value = userPassword,
                onValueChange = { userPassword = it })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

            ForgotPassBtn(
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_normal_3))
                    .align(Alignment.End),
                onClick = { onNavigateResetPassword() })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_11)))

            SignInButton(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                onClick = { signInEmailViewModel.onClickSignIn(userEmail, userPassword) }
            )
            Spacer(modifier = Modifier.weight(1F))

            SignUpHintButton(
                modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
                onClick = { onNavigateSignUp() }
            )
            Spacer(modifier = Modifier.weight(1F))

        } else {
            CircularProgressIndicator()
        }
    }
}
