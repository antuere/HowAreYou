package com.example.zeroapp.presentation.base.ui_compose_components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.zeroapp.R

@Composable
fun NumericKeyPad(
    onClick: (String) -> Unit,
    onClickClear: () -> Unit,
    isShowBiometricBtn: Boolean = false,
    onClickBiometric: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
    ) {
        val numberList = listOf(
            "1",
            "2",
            "3",
            "4",
            "5",
            "6",
            "7",
            "8",
            "9",
        )

        for (i in 0..8 step 3) {
            Row() {
                Button(modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { onClick(numberList[i]) }) {
                    Text(text = numberList[i], color = MaterialTheme.colorScheme.onPrimary)
                }
                Button(modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { onClick(numberList[i + 1]) }) {
                    Text(text = numberList[i + 1], color = MaterialTheme.colorScheme.onPrimary)
                }
                Button(modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { onClick(numberList[i + 2]) }) {
                    Text(text = numberList[i + 2], color = MaterialTheme.colorScheme.onPrimary)
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))
        }
        Row(modifier = Modifier.align(Alignment.End)) {
            if (isShowBiometricBtn) {
                IconButton(modifier = Modifier.padding(horizontal = 16.dp),
                    onClick = { onClickBiometric() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_finger_print),
                        contentDescription = null
                    )
                }
            }

            Button(modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { onClick("0") }) {
                Text(text = "0", color = MaterialTheme.colorScheme.onPrimary)
            }
            TextButton(modifier = Modifier.padding(horizontal = 16.dp),
                onClick = { onClickClear() }) {
                Text(
                    text = stringResource(id = R.string.clear_pin),
                    color = MaterialTheme.colorScheme.onSecondary
                )
            }
        }


    }
}