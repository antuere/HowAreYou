package antuere.how_are_you.presentation.base.ui_compose_components.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource

@Composable
fun OutlinedButtonWithIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isIconInStart: Boolean = true,
    @StringRes labelId: Int,
    @DrawableRes iconId: Int
) {
    OutlinedButton(
        modifier = modifier,
        onClick = { onClick() },
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
    ) {
        if (isIconInStart) {
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize),
                tint = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(id = labelId),
                color = MaterialTheme.colorScheme.secondary
            )
        } else {
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Text(
                stringResource(id = labelId),
                color = MaterialTheme.colorScheme.secondary
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                painter = painterResource(id = iconId),
                contentDescription = "Localized description",
                tint = MaterialTheme.colorScheme.secondary
            )
        }

    }
}