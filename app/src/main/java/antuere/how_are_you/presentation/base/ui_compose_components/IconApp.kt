package antuere.how_are_you.presentation.base.ui_compose_components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import antuere.how_are_you.R

@Composable
fun IconApp(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.drawable.icon_app),
        contentDescription = "Icon app",
        modifier = modifier
    )
}