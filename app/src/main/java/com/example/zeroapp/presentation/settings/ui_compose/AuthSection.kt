package com.example.zeroapp.presentation.settings.ui_compose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import antuere.domain.util.Constants
import com.example.zeroapp.R

@Composable
fun AuthSection(
    modifier: Modifier = Modifier,
    userName: String,
    onClickSignIn: () -> Unit,
    onClickSignOut: () -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.Start
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_8)))
        Text(
            modifier = modifier,
            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp,
            text = if (userName != Constants.USER_NOT_AUTH) "${stringResource(id = R.string.hello_user)} $userName" else stringResource(
                id = R.string.hello_user_plug
            )
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

        Text(
            modifier = modifier,
            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
            text = stringResource(id = R.string.how_are_you_text)
        )

        if (userName == Constants.USER_NOT_AUTH) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
            Text(
                modifier = modifier,
                fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
                text = stringResource(id = R.string.sign_in_advice)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

            Button(
                onClick = { onClickSignIn() },
                modifier = modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    modifier = modifier,
                    text = stringResource(id = R.string.sign_in),
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        } else {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))

            OutlinedButton(
                onClick = { onClickSignOut() },
                modifier = modifier.align(Alignment.End)
            ) {
                Text(text = stringResource(id = R.string.sign_out))
            }
        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        Divider(modifier = modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.onSecondary)
    }
}