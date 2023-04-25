package antuere.how_are_you.presentation.base.ui_compose_components.card

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults

@Composable
fun CardWithFab(
    modifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    textAlignment: Alignment = Alignment.Center,
    titleText: String,
    content: @Composable ColumnScope.() -> Unit = {},
) {
    GradientCard(
        modifier = modifier,
        shape = shape,
        colors = colors,
        elevation = elevation
    ) {
        Box(modifier = textModifier) {
            Text(
                text = titleText,
                modifier = Modifier.align(textAlignment),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleMedium
            )
        }
        content.invoke(this)
    }
}

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
    imageAlignment: Alignment = Alignment.Center,
) {
    GradientCardWithOnClick(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        colors = colors,
        elevation = elevation,
        gradient = GradientDefaults.primary()
    ) {
        Box(
            modifier = Modifier
                .weight(0.68F)
                .fillMaxSize()
                .clip(shape = MaterialTheme.shapes.large)
                .background(GradientDefaults.secondary()),
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
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}

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
    GradientCardWithOnClick(
        modifier = modifier,
        onClick = onClick,
        shape = shape,
        colors = colors,
        elevation = elevation,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_2)))
            leadingIconRes?.let { iconRes ->
                Icon(
                    modifier = Modifier.size(35.dp),
                    painter = painterResource(id = iconRes),
                    contentDescription = leadingIconDescReS?.let { stringResource(id = it) },
                    tint = Color.Unspecified
                )
            }

            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_5)))
            Text(
                text = stringResource(id = labelRes),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
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