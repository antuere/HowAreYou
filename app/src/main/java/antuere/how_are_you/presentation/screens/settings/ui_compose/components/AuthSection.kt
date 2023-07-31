package antuere.how_are_you.presentation.screens.settings.ui_compose.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import antuere.domain.util.Constants
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.divider.DefaultDivider

@Composable
fun AuthSection(
    userName: String,
    onClickSignIn: () -> Unit,
    onClickSignInAdvice: () -> Unit,
    onClickAccountSettings: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        Text(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_normal_2)
            ),
            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp,
            text = if (userName != Constants.USER_NOT_AUTH) "${stringResource(id = R.string.hello_user)} $userName" else stringResource(
                id = R.string.hello_user_plug
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_0)))

        Text(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2)),
            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
            text = stringResource(id = R.string.how_are_you_text)
        )

        Crossfade(targetState = userName != Constants.USER_NOT_AUTH) { isUserAuth ->
            when (isUserAuth) {
                true -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start
                    ) {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

                        OutlinedButton(
                            onClick = { onClickAccountSettings() },
                            modifier = Modifier
                                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2))
                                .align(Alignment.End)
                        ) {
                            Text(text = stringResource(id = R.string.account_settings))
                        }
                    }
                }

                false -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_0)))

                        SignInAdviceRow(
                            modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_normal_2)),
                            onClickInfo = onClickSignInAdvice
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

                        Button(
                            onClick = { onClickSignIn() },
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = dimensionResource(id = R.dimen.default_elevation),
                                pressedElevation = dimensionResource(id = R.dimen.pressed_elevation)
                            ),
                            modifier = Modifier
                                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2))
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2)),
                                text = stringResource(id = R.string.sign_in),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        DefaultDivider(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2))
        )
    }
}