package com.example.zeroapp.presentation.base.ui_compose_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.zeroapp.R

@Composable
fun NumericKeyPad(
    onClick: (String) -> Unit,
    onClickClear: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))) {
        var value = 1
        repeat(3) {
            Row() {
                Button(modifier = Modifier.padding(horizontal = 2.dp),
                    onClick = { onClick("$value") }) {
                    Text(text = "$value")
                }
                Button(modifier = Modifier.padding(horizontal = 2.dp),
                    onClick = { onClick("${value + 1}") }) {
                    Text(text = "${value + 1}")
                }
                Button(modifier = Modifier.padding(horizontal = 2.dp),
                    onClick = { onClick("${value + 2}") }) {
                    Text(text = "${value + 2}")
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
            value += 3
        }
        Row(horizontalArrangement = Arrangement.End) {
            Button(modifier = Modifier.padding(horizontal = 2.dp),
                onClick = { onClick("0") }) {
                Text(text = "0")
            }
            Button(modifier = Modifier.padding(horizontal = 2.dp),
                onClick = { onClickClear() }) {
                Text(text = stringResource(id = R.string.clear_pin))
            }
        }


    }
}