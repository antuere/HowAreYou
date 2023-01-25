package antuere.how_are_you.presentation.sign_up_with_email

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.IconApp
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultButton
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.DefaultTextField
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.EmailTextField
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.PasswordTextField
import antuere.how_are_you.util.paddingTopBar

@Composable
fun SignUpEmailScreen(
    modifier: Modifier = Modifier,
    updateAppBar: (AppBarState) -> Unit,
    showSnackbar: (String) -> Unit,
    onNavigateUp: () -> Unit,
    onNavigateSettings: () -> Unit,
    signUpEmailViewModel: SignUpEmailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val isShowRegisterProgressIndicator by signUpEmailViewModel.isShowRegisterProgressIndicator.collectAsState()
    val signUpState by signUpEmailViewModel.signUpState.collectAsState()

    var userNickname by remember { mutableStateOf("") }
    var userEmail by remember { mutableStateOf("") }
    var userPassword by remember { mutableStateOf("") }
    var userConfirmedPassword by remember { mutableStateOf("") }

    LaunchedEffect(true) {
        updateAppBar(
            AppBarState(
                titleId = R.string.sign_up,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() },
                isVisibleBottomBar = false
            ),
        )
    }

    LaunchedEffect(signUpState){
        signUpState?.let { state ->
            when (state) {
                is SignUpState.Successful -> {
                    onNavigateSettings()
                    signUpEmailViewModel.resetIsShowRegisterProgressIndicator(true)

                }
                is SignUpState.EmptyFields -> {
                    showSnackbar(state.message.asString(context))
                }
                is SignUpState.PasswordsError -> {
                    showSnackbar(state.message.asString(context))
                }

                is SignUpState.ErrorFromFireBase -> {
                    showSnackbar(state.message.asString(context))
                    signUpEmailViewModel.resetIsShowRegisterProgressIndicator()
                }
            }
            signUpEmailViewModel.nullifyState()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar(),
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

            DefaultButton(
                modifier = modifier.padding(bottom = dimensionResource(id = R.dimen.padding_large_1)),
                labelId = R.string.sign_up,
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