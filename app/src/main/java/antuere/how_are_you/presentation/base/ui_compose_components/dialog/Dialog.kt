package antuere.how_are_you.presentation.base.ui_compose_components.dialog

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@Composable
fun Dialog(
    dialog: UIDialog,
    iconColor: Color = MaterialTheme.colorScheme.onBackground,
    closeDialog: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = {
            dialog.dismissAction()
            closeDialog()
        },
        icon = {
            Icon(
                painterResource(id = dialog.icon),
                contentDescription = null
            )
        },
        title = {
            Text(stringResource(id = dialog.title), fontWeight = FontWeight.Medium)
        },
        text = {
            Text(
                text = stringResource(id = dialog.desc),
                textAlign = TextAlign.Center
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    dialog.positiveButton.onClick()
                    closeDialog()
                }
            ) {
                Text(
                    stringResource(id = dialog.positiveButton.text),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    dialog.negativeButton.onClick()
                    closeDialog()
                }
            ) {
                Text(
                    stringResource(id = dialog.negativeButton.text),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        iconContentColor = iconColor
    )
}
