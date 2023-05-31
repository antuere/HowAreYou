package antuere.how_are_you.presentation.screens.helplines.ui_compose.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.domain.dto.helplines.Helpline
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.card.GradientCardWithOnClick
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults

@Composable
fun HelplineItem(
    helpline: Helpline,
    onClickToItem: () -> Unit,
    onClickPhone: (String) -> Unit,
    onClickWebsite: (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f
    )

    GradientCardWithOnClick(
        modifier = Modifier
            .fillMaxWidth(0.9F)
            .padding(vertical = dimensionResource(id = R.dimen.padding_normal_0))
            .animateContentSize(
                tween(
                    durationMillis = 70,
                    easing = LinearOutSlowInEasing
                )
            ),
        shape = MaterialTheme.shapes.large,
        onClick = {
            isExpanded = !isExpanded
            if (isExpanded) {
                onClickToItem()
            }
        },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        elevation = CardDefaults.cardElevation(0.dp),
        gradient = GradientDefaults.surface()
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
        Row(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_normal_1)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier
                    .weight(8f),
                fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp,
                fontWeight = FontWeight.Medium,
                maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
                text = stringResource(id = helpline.nameResId),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )

            IconButton(
                modifier = Modifier
                    .weight(1f)
                    .rotate(rotationState),
                onClick = {
                    isExpanded = !isExpanded
                    if (isExpanded) {
                        onClickToItem()
                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "DropDown Arrow",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
        AnimatedVisibility(
            visible = isExpanded,
            exit = fadeOut(
                tween(
                    durationMillis = 10,
                    easing = LinearOutSlowInEasing
                )
            ) + shrinkVertically(
                tween(
                    durationMillis = 200,
                    easing = LinearOutSlowInEasing
                )
            )
        ) {
            DetailsForHelpline(
                description = stringResource(id = helpline.descResId),
                phone = helpline.phone,
                website = helpline.website,
                onClickPhone = onClickPhone,
                onClickWebsite = onClickWebsite
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
    }
}