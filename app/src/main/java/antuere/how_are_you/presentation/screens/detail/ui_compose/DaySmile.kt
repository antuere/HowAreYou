package antuere.how_are_you.presentation.screens.detail.ui_compose

import androidx.compose.foundation.Image
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun DaySmile(
    modifier: Modifier = Modifier,
    smileRes: Int,
) {
    Image(
        modifier = modifier,
        painter = painterResource(id = smileRes),
        colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
        contentDescription = null,
        contentScale = ContentScale.Crop
    )
}