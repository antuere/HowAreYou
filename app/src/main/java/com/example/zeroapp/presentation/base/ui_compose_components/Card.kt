package com.example.zeroapp.presentation.base.ui_compose_components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

@Composable
fun CardDefault(
    cardModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    textAlignment: Alignment = Alignment.Center,
    titleText: String,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Card(
        modifier = cardModifier,
        shape = shape,
        colors = colors,
        elevation = elevation
    ) {
        Box(modifier = textModifier) {
            Text(
                text = titleText,
                Modifier.align(textAlignment),
            )
        }
        content.invoke(this)
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CardWithOnClick(
    cardModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    textAlignment: Alignment = Alignment.Center,
    titleText: String,
    content: @Composable ColumnScope.() -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = cardModifier,
        shape = shape,
        colors = colors,
        elevation = elevation
    ) {
        Box(modifier = textModifier.fillMaxSize()) {
            Text(text = titleText, Modifier.align(textAlignment))
        }
        content.invoke(this)
    }
}