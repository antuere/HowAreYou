package com.example.zeroapp.presentation.sign_in_with_email

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.IconApp
import com.example.zeroapp.presentation.base.ui_compose_components.buttons.DefaultButton
import com.example.zeroapp.presentation.base.ui_compose_components.buttons.DefaultTextButton
import com.example.zeroapp.presentation.base.ui_compose_components.text_field.EmailTextField
import com.example.zeroapp.presentation.base.ui_compose_components.text_field.PasswordTextField
import com.example.zeroapp.util.paddingTopBar

@Composable
fun SignInEmailScreen(
    modifier: Modifier = Modifier,
    updateAppBar: (AppBarState) -> Unit,
    showSnackbar: (String) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateSettings: () -> Unit,
    onNavigateSignUp: () -> Unit,
    onNavigateResetPassword: () -> Unit,
    signInEmailViewModel: SignInEmailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val isShowLoginProgressIndicator by signInEmailViewModel.isShowLoginProgressIndicator.collectAsState()
    val signInState by signInEmailViewModel.signInState.collectAsState()

    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        updateAppBar(
            AppBarState(
                titleId = R.string.sign_in,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() },
                isVisibleBottomBar = false
            )
        )
    }

    LaunchedEffect(signInState) {
        signInState?.let { state ->
            when (state) {
                is SignInState.Successful -> {
                    onNavigateSettings()
                    signInEmailViewModel.resetIsShowLoginProgressIndicator(true)
                }
                is SignInState.EmptyFields -> {
                    showSnackbar(state.message.asString(context))
                }

                is SignInState.ErrorFromFireBase -> {
                    showSnackbar(state.message.asString((context)))
                    signInEmailViewModel.resetIsShowLoginProgressIndicator()
                }
            }
            signInEmailViewModel.nullifyState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
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

            DefaultTextButton(
                modifier = Modifier
                    .padding(end = dimensionResource(id = R.dimen.padding_normal_3))
                    .align(Alignment.End),
                labelId = R.string.reset_password_hint,
                onClick = { onNavigateResetPassword() })
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_11)))

            DefaultButton(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                labelId = R.string.sign_in,
                onClick = { signInEmailViewModel.onClickSignIn(userEmail, userPassword) }
            )
            Spacer(modifier = Modifier.weight(1F))

            DefaultTextButton(
                modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
                labelId = R.string.don_have_acc,
                onClick = { onNavigateSignUp() }
            )
            Spacer(modifier = Modifier.weight(1F))

        } else {
            Spacer(modifier = Modifier.weight(0.7F))
            CircularProgressIndicator()
            Spacer(modifier = Modifier.weight(1F))
        }
    }
}
