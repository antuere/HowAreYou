package com.example.zeroapp.presentation.sign_in_with_email.ui_compose

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.zeroapp.R

@Composable
fun SignInButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = { onClick() }) {
        Text(
            text = stringResource(id = R.string.sign_in),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}