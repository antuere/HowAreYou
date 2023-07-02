package antuere.how_are_you.presentation.screens.sign_in_methods.ui_compose

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import antuere.domain.util.Constants
import antuere.how_are_you.R

@Composable
fun PrivacyPolicyInfo(
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit,
) {
    val resultedText = buildAnnotatedString {
        append("${stringResource(R.string.privacy_policy_info_start)} ")
        pushStringAnnotation(tag = "click", annotation = Constants.PRIVACY_POLICY_LINK)
        withStyle(
            SpanStyle(
                textDecoration = TextDecoration.Underline,
                color = MaterialTheme.colorScheme.primary
            )
        ) {
            append(stringResource(R.string.privacy_policy_info_end))
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
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    )
}