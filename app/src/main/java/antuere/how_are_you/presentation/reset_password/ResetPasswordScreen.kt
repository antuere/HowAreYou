package antuere.how_are_you.presentation.reset_password

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.top_bar.AppBarState
import antuere.how_are_you.presentation.base.ui_compose_components.buttons.DefaultButton
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.EmailTextField
import antuere.how_are_you.util.paddingTopBar

@Composable
fun ResetPasswordScreen(
    onNavigateUp: () -> Unit,
    updateAppBar: (AppBarState) -> Unit,
    showSnackbar: (String) -> Unit,
    resetPasswordViewModel: ResetPasswordViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    val isShowProgressIndicator by resetPasswordViewModel.isShowProgressIndicator.collectAsState()
    val resetState by resetPasswordViewModel.resetState.collectAsState()
    var userEmail by remember { mutableStateOf("") }
    
    LaunchedEffect(true) {
        updateAppBar(
            AppBarState(
                titleId = R.string.reset_password,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() },
                isVisibleBottomBar = false
            )
        )
    }

   LaunchedEffect(resetState){
       resetState?.let { state ->
           when (state) {
               is ResetPasswordState.Successful -> {
                   showSnackbar(state.message.asString(context))
                   onNavigateUp()
                   resetPasswordViewModel.resetIsShowLoginProgressIndicator()
               }
               is ResetPasswordState.EmptyFields -> {
                   showSnackbar(state.message.asString(context))
               }

               is ResetPasswordState.ErrorFromFireBase -> {
                   showSnackbar(state.message.asString(context))
                   resetPasswordViewModel.resetIsShowLoginProgressIndicator()
               }
           }
           resetPasswordViewModel.nullifyState()
       }
   }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (!isShowProgressIndicator) {
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
        } else {
            CircularProgressIndicator()
        }

    }
}
