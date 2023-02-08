package antuere.how_are_you.presentation.base.ui_image

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource

@Composable
fun DefaultImage(modifier: Modifier = Modifier, imageResId: Int, description: String?) {
    Image(
        modifier = modifier,
        painter = painterResource(id = imageResId),
        contentDescription = description
    )
}
