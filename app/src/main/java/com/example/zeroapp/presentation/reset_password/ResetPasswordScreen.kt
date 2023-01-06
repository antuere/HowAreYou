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
import com.example.zeroapp.presentation.base.ui_compose_components.top_bar.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.buttons.DefaultButton
import com.example.zeroapp.presentation.base.ui_compose_components.text_field.EmailTextField
import com.example.zeroapp.util.ShowSnackBar
import com.example.zeroapp.util.ShowSnackBarWithDelay

@Composable
fun ResetPasswordScreen(
    onNavigateUp: () -> Unit,
    onComposing: (AppBarState, Boolean) -> Unit,
    snackbarHostState: SnackbarHostState,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val resetState by resetPasswordViewModel.resetState.collectAsState()
    var userEmail by remember { mutableStateOf("") }

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

    //    TODO вынести в launched effect
    resetState?.let { state ->
        when (state) {
            is ResetPasswordState.Successful -> {
                snackbarHostState.ShowSnackBar(message = stringResource(id = state.res))
                onNavigateUp()
                resetPasswordViewModel.nullifyState()
            }
            is ResetPasswordState.EmptyFields -> {
                snackbarHostState.ShowSnackBarWithDelay(
                    message = stringResource(id = state.res)
                ) {
                    resetPasswordViewModel.nullifyState()
                }
            }

            is ResetPasswordState.ErrorFromFireBase -> {
                snackbarHostState.ShowSnackBarWithDelay(
                    message = state.message
                ) {
                    resetPasswordViewModel.nullifyState()
                }
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
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

        DefaultButton(
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
            labelId = R.string.reset_password,
            onClick = { resetPasswordViewModel.onClickResetPassword(userEmail) }
        )
    }
}
