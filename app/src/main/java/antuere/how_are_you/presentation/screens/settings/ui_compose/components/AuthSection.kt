package antuere.how_are_you.presentation.screens.settings.ui_compose.components

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import antuere.domain.util.Constants
import antuere.how_are_you.R

@Composable
fun AuthSection(
    userName: String,
    onClickSignIn: () -> Unit,
    onClickSignOut: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_8)))
        Text(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_normal_3)
            ),
            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp,
            text = if (userName != Constants.USER_NOT_AUTH) "${stringResource(id = R.string.hello_user)} $userName" else stringResource(
                id = R.string.hello_user_plug
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

        Text(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.padding_normal_3)
            ),
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
                            onClick = { onClickSignOut() },
                            modifier = Modifier
                                .padding(
                                    horizontal = dimensionResource(id = R.dimen.padding_normal_3)
                                )
                                .align(Alignment.End)
                        ) {
                            Text(text = stringResource(id = R.string.sign_out))
                        }
                    }
                }
                false -> {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.Start,
                    ) {
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
                        Text(
                            modifier = Modifier.padding(
                                horizontal = dimensionResource(id = R.dimen.padding_normal_3)
                            ),
                            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
                            text = stringResource(id = R.string.sign_in_advice)
                        )
                        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

                        ElevatedButton(
                            onClick = { onClickSignIn() },
                            colors = ButtonDefaults.buttonColors(),
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = dimensionResource(id = R.dimen.default_elevation),
                                pressedElevation = dimensionResource(id = R.dimen.pressed_elevation)
                            ),
                            modifier = Modifier
                                .padding(
                                    horizontal = dimensionResource(id = R.dimen.padding_normal_3)
                                )
                                .align(Alignment.CenterHorizontally)
                        ) {
                            Text(
                                modifier = Modifier.padding(
                                    horizontal = dimensionResource(id = R.dimen.padding_normal_3)
                                ),
                                text = stringResource(id = R.string.sign_in),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        Divider(
            modifier = Modifier
                .padding(
                    horizontal = dimensionResource(id = R.dimen.padding_normal_3)
                )
                .fillMaxWidth(), color = MaterialTheme.colorScheme.onSecondary
        )
    }
}