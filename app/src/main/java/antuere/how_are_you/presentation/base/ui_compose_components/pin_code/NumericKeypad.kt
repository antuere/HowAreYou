package antuere.how_are_you.presentation.base.ui_compose_components.pin_code

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.util.extensions.fixedSize

@Composable
fun NumericKeypad(
    onClick: (String) -> Unit,
    onClickClear: () -> Unit,
    isShowBiometricBtn: Boolean = false,
    onClickBiometric: () -> Unit = {},
) {
    Column(
        modifier = Modifier
            .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_1))
    ) {
        val numbersList = remember {
            listOf(
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
        }

        for (i in 0..8 step 3) {
            Row {
                Button(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .size(55.dp),
                    onClick = { onClick(numbersList[i]) },
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = numbersList[i],
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .size(55.dp),
                    onClick = { onClick(numbersList[i + 1]) },
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = numbersList[i + 1],
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp
                    )
                }
                Button(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .size(55.dp),
                    onClick = { onClick(numbersList[i + 2]) },
                    shape = CircleShape,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = numbersList[i + 2],
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        }
        Row(modifier = Modifier.align(Alignment.End)) {
            if (isShowBiometricBtn) {
                IconButton(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .size(55.dp),
                    onClick = { onClickBiometric() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_finger_print),
                        contentDescription = null
                    )
                }
            }

            Button(
                modifier = Modifier
                    .padding(horizontal = 20.dp)
                    .size(55.dp),
                onClick = { onClick("0") },
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp)
            ) {
                Text(
                    text = "0",
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp
                )
            }
            TextButton(
                modifier = Modifier
                    .padding(horizontal = 17.5.dp)
                    .size(60.dp),
                onClick = { onClickClear() },
                contentPadding = PaddingValues(
                    start = 2.dp,
                    top = ButtonDefaults.ContentPadding.calculateTopPadding(),
                    end = 2.dp,
                    bottom = ButtonDefaults.ContentPadding.calculateBottomPadding()
                )
            ) {
                Text(
                    text = stringResource(id = R.string.clear_pin),
                    fontSize = 14f.fixedSize,
                    color = MaterialTheme.colorScheme.onSecondary,
                    textAlign = TextAlign.Center,
                    softWrap = false,
                    overflow = TextOverflow.Visible
                )
            }
        }


    }
}