package antuere.how_are_you.presentation.screens.onboard.ui_compose.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_animations.materialSharedAxisZIn
import antuere.how_are_you.presentation.base.ui_animations.materialSharedAxisZOut

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun EnterInAppButton(
    modifier: Modifier = Modifier,
    isVisible: Boolean,
    onClick: () -> Unit,
) {
    AnimatedContent(
        modifier = modifier,
        targetState = isVisible,
        transitionSpec = {
            materialSharedAxisZIn(forward = true, durationMillis = 400) with
                    materialSharedAxisZOut(forward = false, durationMillis = 400)
        }
    ) { state ->
        when (state) {
            true -> {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onClick
                ) {
                    Text(
                        text = stringResource(id = R.string.onboard_enter_btn),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            false -> {
                Spacer(
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth()
                )
            }
        }

    }
}