package antuere.how_are_you.presentation.helplines.ui_compose

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import antuere.domain.dto.helplines.Helpline
import antuere.how_are_you.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HelplineItem(
    helpline: Helpline,
    onClickPhone: (String) -> Unit,
    onClickWebsite: (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (isExpanded) 180f else 0f
    )

    Card(
        modifier = Modifier
            .fillMaxWidth(0.9F)
            .padding(vertical = dimensionResource(id = R.dimen.padding_normal_0))
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300
                )
            ),
        shape = MaterialTheme.shapes.large,
        onClick = { isExpanded = !isExpanded },
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_1)),
                fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp,
                fontWeight = FontWeight.Medium,
                text = stringResource(id = helpline.nameResId),
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier
                    .rotate(rotationState)
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_1)),
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "DropDown Arrow",
                tint = MaterialTheme.colorScheme.onSecondary
            )

        }
        if (isExpanded) {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))
            DetailsForHelpline(
                phone = helpline.phone,
                onClickPhone = onClickPhone,
                onClickWebsite = onClickWebsite
            )
        }
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
    }
}