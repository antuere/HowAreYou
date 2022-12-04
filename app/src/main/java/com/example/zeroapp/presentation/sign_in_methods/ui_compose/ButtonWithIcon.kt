package com.example.zeroapp.presentation.sign_in_methods.ui_compose

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.zeroapp.R

@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    @StringRes labelId: Int,
    @DrawableRes iconId: Int
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
    ) {
        Icon(
            painter = painterResource(id = iconId),
            contentDescription = "Localized description",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
        Text(
            stringResource(id = labelId),
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}