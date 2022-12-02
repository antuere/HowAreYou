package com.example.zeroapp.presentation.sign_in_with_email.ui_compose

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.zeroapp.R

@Composable
fun SignUpHintButton(
    modifier: Modifier = Modifier,
    onClick : () -> Unit
) {
    TextButton(
        modifier = modifier,
        onClick = { onClick() },
    ) {
        Text(
            text = stringResource(id = R.string.don_have_acc),
            color = MaterialTheme.colorScheme.onSecondary
        )
    }
}