package antuere.how_are_you.presentation.add_day.ui_compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import antuere.how_are_you.R
import kotlinx.collections.immutable.ImmutableList

@Composable
fun SmileRow(
    smileImages: ImmutableList<Int>,
    onClick: (Int) -> Unit,
) {
    Row(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
    ) {
        smileImages.forEach { imageRes ->
            IconButton(
                onClick = { onClick(imageRes) }) {
                Icon(painter = painterResource(id = imageRes), contentDescription = null)
            }
        }
    }
}