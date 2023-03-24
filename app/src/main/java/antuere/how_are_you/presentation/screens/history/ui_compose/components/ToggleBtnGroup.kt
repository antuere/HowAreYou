package antuere.how_are_you.presentation.screens.history.ui_compose.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import antuere.domain.dto.ToggleBtnState
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_text.UiText
import timber.log.Timber

@Composable
fun ToggleBtnGroup(
    modifier: Modifier = Modifier,
    toggleButtons: Map<UiText, ToggleBtnState>,
    currentToggleBtnState: ToggleBtnState,
    onClick: (ToggleBtnState) -> Unit,
) {
    val shapePercent = 50

    Timber.i("MVI error test : composed in toggleBtnGroup ${currentToggleBtnState.name}")

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        toggleButtons.entries.forEachIndexed { index, entry ->
            OutlinedButton(
                modifier = when (index) {
                    0 ->
                        Modifier
                            .width(dimensionResource(id = R.dimen.checked_button_width))
                            .offset(0.dp, 0.dp)
                            .zIndex(if (currentToggleBtnState == entry.value) 1f else 0f)
                    else ->
                        Modifier
                            .width(dimensionResource(id = R.dimen.checked_button_width))
                            .offset((-1 * index).dp, 0.dp)
                            .zIndex(if (currentToggleBtnState == entry.value) 1f else 0f)
                },
                onClick = {
                    if (currentToggleBtnState != entry.value) {
                        onClick(entry.value)
                    }
                },
                shape = when (index) {
                    // left outer button
                    0 -> RoundedCornerShape(
                        topStartPercent = shapePercent,
                        topEndPercent = 0,
                        bottomStartPercent = shapePercent,
                        bottomEndPercent = 0
                    )
                    // right outer button
                    toggleButtons.size - 1 -> RoundedCornerShape(
                        topStartPercent = 0,
                        topEndPercent = shapePercent,
                        bottomStartPercent = 0,
                        bottomEndPercent = shapePercent
                    )
                    // middle button
                    else -> RoundedCornerShape(0.dp)
                },
                border = BorderStroke(width = 1.dp, color = Color.Gray),
                colors = if (currentToggleBtnState == entry.value) {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface.copy(
                            alpha = 0.5f
                        ), contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                } else {
                    ButtonDefaults.outlinedButtonColors(
                        containerColor = MaterialTheme.colorScheme.onPrimary,
                        contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
            ) {
                Text(
                    text = entry.key.asString(),
                    modifier = Modifier.padding(horizontal = 8.dp),
                    softWrap = false
                )
            }
        }
    }
}