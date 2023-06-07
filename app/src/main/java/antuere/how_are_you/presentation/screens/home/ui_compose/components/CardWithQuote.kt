package antuere.how_are_you.presentation.screens.home.ui_compose.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.card.GradientCard
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults
import antuere.how_are_you.util.extensions.fixedSize

@Composable
fun CardWithQuote(
    modifier: Modifier = Modifier,
    shape: Shape = MaterialTheme.shapes.large,
    colors: CardColors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    elevation: CardElevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    titleText: String,
    quoteText: String,
    quiteAuthor: String,
) {
    GradientCard(
        modifier = modifier,
        colors = colors,
        shape = shape,
        elevation = elevation,
        gradient = GradientDefaults.primary()
    ) {
        Row(
            modifier = Modifier
                .weight(0.3F)
                .fillMaxSize()
                .clip(shape = MaterialTheme.shapes.large)
                .background(GradientDefaults.secondary()),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_2)))
            Icon(
                modifier = Modifier.size(35.dp),
                painter = painterResource(id = R.drawable.ic_quote),
                contentDescription = "Quote icon",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(dimensionResource(id = R.dimen.spacer_height_2)))
            Text(
                text = titleText,
                fontSize = dimensionResource(id = R.dimen.textSize_normal_1).value.fixedSize,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .weight(0.7F)
                .fillMaxSize()
                .padding(
                    start = dimensionResource(id = R.dimen.padding_normal_1),
                    end = dimensionResource(id = R.dimen.padding_normal_1),
                    bottom = dimensionResource(id = R.dimen.padding_normal_0),
                    top = dimensionResource(id = R.dimen.padding_small_1)
                )
        ) {
            Text(
                text = "\"$quoteText\"",
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = dimensionResource(id = R.dimen.padding_small_0)),
                fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.fixedSize,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                lineHeight = 22.sp
            )

            Spacer(modifier = Modifier.weight(1F))
            Text(
                text = quiteAuthor,
                modifier = Modifier.align(Alignment.End),
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Normal)
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