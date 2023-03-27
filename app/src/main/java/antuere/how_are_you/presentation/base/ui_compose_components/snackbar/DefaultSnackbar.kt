package antuere.how_are_you.presentation.base.ui_compose_components.snackbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun DefaultSnackbar(text: String) {
    Card(
        modifier = Modifier.padding(16.dp),
        shape = ShapeDefaults.Large,
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
        )
    ) {
        Box(
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.displaySmall,
                textAlign = TextAlign.Center
            )
        }
    }
}