package com.example.zeroapp.presentation.base.ui_compose_components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.zeroapp.presentation.base.ui_compose_components.dialog.UIDialogCompose

@Composable
fun Dialog(dialog: UIDialogCompose){
    AlertDialog(
        onDismissRequest = dialog.negativeButton.onClick,
        icon = {
            Icon(
                painterResource(id = dialog.icon),
                contentDescription = null
            )
        },
        title = {
            Text(stringResource(id = dialog.title))
        },
        text = {
            Text(stringResource(id = dialog.desc))
        },
        confirmButton = {
            TextButton(
                onClick = {
                    dialog.positiveButton.onClick.invoke()
                }
            ) {
                Text(stringResource(id = dialog.positiveButton.text))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dialog.negativeButton.onClick.invoke()
                }
            ) {
                Text(stringResource(id = dialog.negativeButton.text))
            }
        }
    )
}