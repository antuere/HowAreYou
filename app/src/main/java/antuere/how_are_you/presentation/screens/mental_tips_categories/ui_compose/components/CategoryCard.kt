package antuere.how_are_you.presentation.screens.mental_tips_categories.ui_compose.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import antuere.domain.dto.mental_tips.MentalTipsCategory
import antuere.how_are_you.R
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryCard(
    modifier: Modifier = Modifier,
    category: MentalTipsCategory,
    onClick: (String) -> Unit,
) {
    Timber.i("MVI error test : enter in category card")
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleCard by animateFloatAsState(if (isPressed) 0.99f else 1f)

    ElevatedCard(
        modifier = modifier.graphicsLayer {
            scaleX = scaleCard
            scaleY = scaleCard
        },
        onClick = { onClick(category.categoryName.name) },
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_2)))
            Image(
                modifier = Modifier.fillMaxHeight(0.85f),
                painter = painterResource(id = category.iconRes),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_5)))

            Text(text = stringResource(id = category.textRes))
            Spacer(modifier = Modifier.weight(1F))

            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSecondary
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_2)))
        }
    }
}