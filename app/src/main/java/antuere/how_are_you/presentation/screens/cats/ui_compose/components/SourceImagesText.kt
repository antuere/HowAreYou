package antuere.how_are_you.presentation.screens.cats.ui_compose.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import antuere.how_are_you.R

@Composable
fun SourceImagesText(
    modifier: Modifier = Modifier,
    webSite: String,
    sourceName: String,
    onClick: (String) -> Unit,
) {
    val startText = stringResource(R.string.photos_by)
    val resultedText = buildAnnotatedString {
        append("$startText ")
        pushStringAnnotation(tag = "click", annotation = webSite)
        withStyle(
            SpanStyle(
                textDecoration = TextDecoration.Underline,
            )
        ) {
            append(sourceName)
        }
        pop()
    }

    ClickableText(
        modifier = modifier,
        text = resultedText,
        onClick = { offset ->
            resultedText.getStringAnnotations(
                tag = "click", start = offset, end = offset
            ).firstOrNull()?.let { annotation ->
                onClick(annotation.item)
            }
        },
        style = MaterialTheme.typography.bodySmall.copy(
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}