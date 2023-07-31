package antuere.how_are_you.presentation.screens.customization.ui_compose.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.divider.DefaultDivider

@Composable
fun FontSizeSettings(
    fontSize: Int,
    onChangeFontSize: (Int) -> Unit,
) {
    var sliderPosition by remember { mutableStateOf(fontSize.toFloat()) }

    Column {
        Text(text = stringResource(R.string.day_description_text_size))
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Slider(
                modifier = Modifier
                    .semantics { contentDescription = "FontSize slider" }
                    .weight(10f),
                value = sliderPosition,
                onValueChange = { sliderPosition = it },
                valueRange = 12f..30f,
                onValueChangeFinished = { onChangeFontSize(sliderPosition.toInt()) },
                steps = 17
            )
            Text(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 4.dp),
                text = sliderPosition.toInt().toString(),
            )

        }

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        Text(
            text = stringResource(R.string.day_customization_example_text),
            fontSize = sliderPosition.sp,
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

        DefaultDivider()
    }
}