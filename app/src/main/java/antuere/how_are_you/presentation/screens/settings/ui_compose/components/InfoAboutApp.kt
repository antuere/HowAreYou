package antuere.how_are_you.presentation.screens.settings.ui_compose.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontWeight
import antuere.how_are_you.BuildConfig
import antuere.how_are_you.R

@Composable
fun InfoAboutApp(
    modifier: Modifier = Modifier,
) {
    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
    Text(
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8F),
        ),
         text = "HowAreYou v.${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    )
}