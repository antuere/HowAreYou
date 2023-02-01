package antuere.how_are_you.presentation.helplines.ui_compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R

@Composable
fun DetailsForHelpline(
    phone: String,
    onClickPhone: (String) -> Unit,
    onClickWebsite: (String) -> Unit,
) {
    Column(modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2))) {
        Text(
            fontSize = dimensionResource(id = R.dimen.textSize_normal_1).value.sp,
            textAlign = TextAlign.Start,
            text = "Telefono Azzurro provides 24/7, free, compassionate and confidential support by phone and online chat. We support youth in Italy who may require support with abuse & domestic violence, anxiety, bullying, depression, eating & body image, family issues, gambling, gender & sexual identity, grief & loss, loneliness, parenting, pregnancy & abortion, relationships, school or work issues, self-harm, stress, substance use, suicide, supporting a friend or family member, physical illness, trauma & PTSD.",
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
        Row(
            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small_1)),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedButton(
                modifier = Modifier.weight(5F),
                onClick = { onClickPhone("89289296581") },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    tint = MaterialTheme.colorScheme.onSecondary,
                    painter = painterResource(id = R.drawable.ic_phone),
                    contentDescription = null
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    fontSize = dimensionResource(id = R.dimen.textSize_normal_1).value.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    text = stringResource(R.string.helpline_phone),
                )
            }
            Spacer(modifier = Modifier.weight(1F))

            OutlinedButton(
                modifier = Modifier.weight(5F),
                onClick = { onClickWebsite("https://azzurro.it/") },
                contentPadding = ButtonDefaults.ButtonWithIconContentPadding
            ) {
                Icon(
                    modifier = Modifier.size(ButtonDefaults.IconSize),
                    tint = MaterialTheme.colorScheme.onSecondary,
                    painter = painterResource(id = R.drawable.ic_web),
                    contentDescription = null
                )
                Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                Text(
                    fontSize = dimensionResource(id = R.dimen.textSize_normal_1).value.sp,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    text = stringResource(R.string.helpline_website),
                )
            }
        }
    }
}