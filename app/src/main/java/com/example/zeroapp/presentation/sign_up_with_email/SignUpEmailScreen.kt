package com.example.zeroapp.presentation.sign_up_with_email

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.IconApp
import com.example.zeroapp.presentation.base.ui_compose_components.Screen
import com.example.zeroapp.presentation.base.ui_compose_components.text_field.DefaultTextField
import com.example.zeroapp.presentation.base.ui_compose_components.text_field.EmailTextField
import com.example.zeroapp.presentation.base.ui_compose_components.text_field.PasswordTextField
import com.example.zeroapp.presentation.sign_up_with_email.ui_compose.SignUpButton
import com.example.zeroapp.util.ShowSnackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpEmailScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    onComposing: (AppBarState, Boolean) -> Unit,
    signUpEmailViewModel: SignUpEmailViewModel = hiltViewModel()
) {
    val isShowRegisterProgressIndicator by signUpEmailViewModel.isShowRegisterProgressIndicator.collectAsState()
    val signUpState by signUpEmailViewModel.signUpState.collectAsState()

    var userNickname by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userConfirmedPassword by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                titleId = R.string.sign_up,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { navController.navigateUp() }
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
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                )
            }
        }
    ) { it ->
        signUpState?.let { state ->
            when (state) {
                is SignUpState.Successful -> {
                    navController.popBackStack(Screen.Settings.route, false)
                    signUpEmailViewModel.resetIsShowRegisterProgressIndicator(true)
                }
                is SignUpState.EmptyFields -> {
                    snackbarHostState.ShowSnackBar(message = stringResource(id = state.res))
                }
                is SignUpState.PasswordsError -> {
                    snackbarHostState.ShowSnackBar(message = stringResource(id = state.res))
                }

                is SignUpState.ErrorFromFireBase -> {
                    snackbarHostState.ShowSnackBar(message = state.message)
                    signUpEmailViewModel.resetIsShowRegisterProgressIndicator()
                }

            }
            signUpEmailViewModel.nullifyState()
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            IconApp(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_small_1)))
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

            if (!isShowRegisterProgressIndicator) {
                DefaultTextField(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                        .fillMaxWidth(),
                    value = userNickname,
                    onValueChange = { userNickname = it },
                    label = stringResource(id = R.string.nickname),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

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
                Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_5)))

                PasswordTextField(
                    modifier = Modifier
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                        .fillMaxWidth(),
                    labelId = R.string.confirm_password,
                    value = userConfirmedPassword,
                    onValueChange = { userConfirmedPassword = it })
                Spacer(modifier = Modifier.weight(1F))

                SignUpButton(
                    modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
                    onClick = {
                        signUpEmailViewModel.onClickSignUp(
                            email = userEmail,
                            password = userPassword,
                            confirmPassword = userConfirmedPassword,
                            name = userNickname
                        )
                    }
                )

                Spacer(modifier = Modifier.weight(1F))
            } else {
                CircularProgressIndicator()
            }
        }


    }
}