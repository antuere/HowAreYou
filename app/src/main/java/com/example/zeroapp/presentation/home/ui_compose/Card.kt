@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.zeroapp.presentation.base.ui_compose_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import com.example.zeroapp.R
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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


@Composable
fun CardWithOnClick(
    cardModifier: Modifier = Modifier,
    textModifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    textAlignment: Alignment = Alignment.Center,
    titleText: String
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
    }
}

@Composable
fun CardWithQuote(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    titleText: String,
    quoteText: String,
    quiteAuthor: String
) {
    Card(modifier = modifier, colors = colors, shape = shape, elevation = elevation) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    dimensionResource(id = R.dimen.padding_normal_2),
                ),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = titleText,
                Modifier.align(Alignment.Start),
                fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))
            Text(
                text = quoteText, Modifier.align(Alignment.Start),
                fontSize = dimensionResource(id = R.dimen.textSize_normal_1).value.sp
            )

            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = quiteAuthor, Modifier.align(Alignment.End),
                fontSize = dimensionResource(id = R.dimen.textSize_normal_1).value.sp,
                fontStyle = FontStyle.Italic
            )

        }
    }
}

@Preview
@Composable
fun PreviewCardWithQuote() {

    CardWithQuote(
        titleText = "Quote of the day",
        quoteText = "Best quote in the world",
        quiteAuthor = "Antuere"
    )
}