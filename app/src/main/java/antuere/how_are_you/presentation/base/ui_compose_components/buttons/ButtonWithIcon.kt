package antuere.how_are_you.presentation.base.ui_compose_components.buttons

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import antuere.how_are_you.R

@Composable
fun ButtonWithIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isIconInStart: Boolean = true,
    @StringRes labelId: Int,
    @DrawableRes iconId: Int
) {
    ElevatedButton(
        modifier = modifier,
        onClick = { onClick() },
        colors = ButtonDefaults.buttonColors(),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = dimensionResource(id = R.dimen.default_elevation),
            pressedElevation = dimensionResource(id = R.dimen.pressed_elevation)
        ),
        contentPadding = ButtonDefaults.ButtonWithIconContentPadding
    ) {
        if (isIconInStart) {
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
        } else {
            Text(
                stringResource(id = labelId),
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                painter = painterResource(id = iconId),
                contentDescription = "Localized description",
                modifier = Modifier.size(ButtonDefaults.IconSize)
            )
        }

    }
}