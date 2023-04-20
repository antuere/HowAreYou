package antuere.how_are_you.presentation.base.ui_compose_components.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import antuere.how_are_you.presentation.base.ui_compose_components.card.GradientCard
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults

@Composable
fun DefaultSnackbar(text: String) {
    GradientCard(
        modifier = Modifier.padding(16.dp),
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(4.dp),
        gradient = GradientDefaults.secondaryInverse()
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
        }
    }
}