package com.example.zeroapp.presentation.history.ui_compose

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextAlign
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_text.UiText

@Composable
fun HistoryHeaderTextPlug(
    modifier: Modifier = Modifier,
    plugText: UiText
) {
    Text(
        text = plugText.asString(),
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.padding_normal_0)),
        textAlign = TextAlign.Center
    )
}