package antuere.how_are_you.presentation.screens.add_day.ui_compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import antuere.how_are_you.R
import antuere.how_are_you.util.extensions.borderWithText
import antuere.how_are_you.util.isKeyboardVisible
import kotlinx.collections.immutable.ImmutableList


@Composable
fun SmileRow(
    smileImages: ImmutableList<Int>,
    onClick: (Int) -> Unit,
) {
    val isVisibleKeyboard by isKeyboardVisible()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2))
            .borderWithText(
                text = stringResource(id = R.string.how_you_mention),
                textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
                color = if (isVisibleKeyboard) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
                strokeWidth = if (isVisibleKeyboard) 2 else 1
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        smileImages.forEach { imageRes ->
            IconButton(
                onClick = { onClick(imageRes) }) {
                Icon(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}