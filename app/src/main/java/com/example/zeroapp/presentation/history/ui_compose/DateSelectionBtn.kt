package com.example.zeroapp.presentation.history.ui_compose

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun DateSelectionBtn(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: String,
    enabled : Boolean = true
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { onClick() },
        shape = ShapeDefaults.Small,
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
            contentColor = MaterialTheme.colorScheme.onSecondary,
            disabledContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            disabledContentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        enabled = enabled
    )
    {
        Text(text = label, style = MaterialTheme.typography.displaySmall)
    }
}