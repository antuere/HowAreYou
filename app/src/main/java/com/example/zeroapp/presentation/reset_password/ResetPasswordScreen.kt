package com.example.zeroapp.presentation.reset_password

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.text_field.EmailTextField
import com.example.zeroapp.presentation.reset_password.ui_compose.ResetButton
import com.example.zeroapp.util.ShowSnackBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetPasswordScreen(
    modifier: Modifier = Modifier,
    onNavigateUp: () -> Unit,
    onComposing: (AppBarState, Boolean) -> Unit,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val resetState by resetPasswordViewModel.resetState.collectAsState()
    var userEmail by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                titleId = R.string.reset_password,
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
        resetState?.let { state ->
            when (state) {
                is ResetPasswordState.Successful -> {
                    snackbarHostState.ShowSnackBar(message = stringResource(id = state.res))
                    onNavigateUp()
                    resetPasswordViewModel.nullifyState()
                }
                is ResetPasswordState.EmptyFields -> {
                    snackbarHostState.ShowSnackBar(message = stringResource(id = R.string.empty_fields))
                    resetPasswordViewModel.nullifyState(true)
                }

                is ResetPasswordState.ErrorFromFireBase -> {
                    snackbarHostState.ShowSnackBar(message = state.message)
                    resetPasswordViewModel.nullifyState(true)
                }
            }

        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))
            Text(text = stringResource(id = R.string.reset_password_plug), fontSize = 18.sp)

            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_8)))
            EmailTextField(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                    .fillMaxWidth(),
                value = userEmail,
                onValueChange = { userEmail = it })

            Spacer(modifier = Modifier.weight(1F))

            ResetButton(
                modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
                onClick = { resetPasswordViewModel.onClickResetPassword(userEmail) }
            )
        }
    }
}