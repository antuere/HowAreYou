package antuere.how_are_you.presentation.screens.detail.ui_compose.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.text_field.DefaultTextField
import antuere.how_are_you.util.extensions.bringIntoViewForFocused

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DayEditTextField(
    modifier: Modifier = Modifier,
    value: () -> String,
    onValueChange: (String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val bringIntoViewRequester = remember { BringIntoViewRequester() }

    DefaultTextField(
        modifier = modifier.bringIntoViewForFocused(
            bringIntoViewRequester = bringIntoViewRequester,
            scope = scope
        ),
        label = stringResource(id = R.string.day_edit_text),
        value = value(),
        onValueChange = onValueChange,
        maxLength = 1000
    )
}