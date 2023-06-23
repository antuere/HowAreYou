package antuere.how_are_you.presentation.screens.helplines.ui_compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer

@Composable
fun ExpandButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    isExpanded: Boolean,
) {
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f
    )

    IconButton(
        modifier = modifier.graphicsLayer {
            rotationZ = rotationState
        },
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Default.ArrowDropDown,
            contentDescription = "DropDown Arrow",
            tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
    }

}