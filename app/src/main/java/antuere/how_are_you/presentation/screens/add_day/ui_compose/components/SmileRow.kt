package antuere.how_are_you.presentation.screens.add_day.ui_compose.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import antuere.how_are_you.R
import antuere.how_are_you.util.dpToPixel
import kotlinx.collections.immutable.ImmutableList
import timber.log.Timber

@OptIn(ExperimentalTextApi::class)
@Composable
fun SmileRow(
    smileImages: ImmutableList<Int>,
    onClick: (Int) -> Unit,
) {
    Timber.i("Canvas check compose : enter in smile row")
    val textMeasurer = rememberTextMeasurer()
    val context = LocalContext.current
    val color = MaterialTheme.colorScheme.onSurfaceVariant
    val textStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium)
    val smilesTitle = stringResource(id = R.string.how_you_mention)

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2))
            .drawWithCache {
                Timber.i("Canvas check compose : draw cache")
                val textSize = textMeasurer.measure(smilesTitle, textStyle).size
                val textWidth = textSize.width
                val textHeight = textSize.height
                val endTextPoint = dpToPixel(20, context) + textWidth
                val path = Path().apply {
                    moveTo(dpToPixel(12, context), 0f)
                    lineTo(0f, 0f)
                    lineTo(0f, size.height)
                    lineTo(size.width, size.height)
                    lineTo(size.width, 0f)
                    lineTo(endTextPoint, 0f)
                }
                val borderWidth = dpToPixel(1, context)
                val cornerRadius = dpToPixel(8, context)
                val textStartX = dpToPixel(16, context)
                val textStartY = -textHeight / 1.7f

                onDrawBehind {
                    Timber.i("Canvas check compose : onDrawBehind")
                    drawPath(
                        path = path,
                        color = color,
                        style = Stroke(
                            width = borderWidth,
                            pathEffect = PathEffect.cornerPathEffect(cornerRadius)
                        )
                    )

                    drawText(
                        textMeasurer = textMeasurer,
                        text = smilesTitle,
                        style = textStyle,
                        topLeft = Offset(x = textStartX, y = textStartY)
                    )
                }
            }
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        smileImages.forEach { imageRes ->
            IconButton(
                onClick = { onClick(imageRes) }) {
                Icon(
                    painter = painterResource(id = imageRes),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}