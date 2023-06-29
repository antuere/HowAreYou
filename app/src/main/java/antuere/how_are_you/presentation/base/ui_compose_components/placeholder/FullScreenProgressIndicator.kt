package antuere.how_are_you.presentation.base.ui_compose_components.placeholder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import antuere.how_are_you.util.extensions.paddingBotAndTopBar

@Composable
fun FullScreenProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .paddingBotAndTopBar(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}