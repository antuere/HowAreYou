package antuere.how_are_you.presentation.base.ui_compose_components.placeholder

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import antuere.how_are_you.R

@Composable
fun FullScreenProgressIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = dimensionResource(id = R.dimen.padding_normal_0)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}