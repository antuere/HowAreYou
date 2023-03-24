package antuere.how_are_you.presentation.base.ui_compose_components.card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R

@Composable
fun CardWithFab(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    textAlignment: Alignment = Alignment.Center,
    titleText: String,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    ElevatedCard(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation
    ) {
        Box(modifier = textModifier) {
            Text(
                text = titleText,
                modifier = Modifier.align(textAlignment),
                color = MaterialTheme.colorScheme.primaryContainer,
                style = MaterialTheme.typography.titleMedium
            )
        }
        content.invoke(this)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardWithImage(
    @DrawableRes imageRes: Int,
    titleText: String,
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    textAlignment: Alignment = Alignment.Center,
    imageAlignment: Alignment = Alignment.Center
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleCard by animateFloatAsState(if (isPressed) 0.98f else 1f)

    ElevatedCard(
        modifier = modifier.graphicsLayer {
            scaleX = scaleCard
            scaleY = scaleCard
        },
        onClick = onClick,
        shape = shape,
        colors = colors,
        elevation = elevation,
        interactionSource = interactionSource
    ) {
        Box(
            modifier = Modifier
                .weight(0.68F)
                .fillMaxSize()
                .clip(shape = MaterialTheme.shapes.large)
                .background(color = MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier.fillMaxHeight(0.85F),
                painter = painterResource(id = imageRes),
                contentScale = ContentScale.Crop,
                alignment = imageAlignment,
                contentDescription = null
            )
        }

        Box(
            modifier = textModifier
                .weight(0.32F)
                .fillMaxSize()
        ) {
            Text(
                text = titleText,
                modifier = Modifier
                    .align(textAlignment),
//                    .padding(horizontal = dimensionResource(R.dimen.padding_small_2))
                textAlign = TextAlign.Center,
                fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardWithIcons(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    @DrawableRes leadingIconRes: Int? = null,
    @StringRes leadingIconDescReS: Int? = null,
    @StringRes labelRes: Int,
    @DrawableRes trailingIconRes: Int? = null,
    @StringRes trailingIconDescReS: Int? = null,
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
) {

    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val scaleCard by animateFloatAsState(if (isPressed) 0.99f else 1f)

    ElevatedCard(
        modifier = modifier.graphicsLayer {
            scaleX = scaleCard
            scaleY = scaleCard
        },
        onClick = onClick,
        shape = shape,
        colors = colors,
        elevation = elevation,
        interactionSource = interactionSource
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_2)))
            leadingIconRes?.let { iconRes ->
                Icon(
                    modifier = Modifier.size(38.dp),
                    painter = painterResource(id = iconRes),
                    contentDescription = leadingIconDescReS?.let { stringResource(id = it) },
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_5)))
            Text(
                text = stringResource(id = labelRes),
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Spacer(modifier = Modifier.weight(1F))

            trailingIconRes?.let { iconRes ->
                Icon(
                    painter = painterResource(id = iconRes),
                    contentDescription = trailingIconDescReS?.let { stringResource(id = it) },
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_2)))
        }
    }
}