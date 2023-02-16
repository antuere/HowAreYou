package antuere.how_are_you.presentation.screens.mental_tips.ui_compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.domain.dto.mental_tips.MentalTip
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_theme.PlayfairDisplay

@Composable
fun MentalTipItem(
    modifier: Modifier = Modifier,
    mentalTip: MentalTip,
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_3)))
        Image(
            modifier = Modifier
                .fillMaxWidth(0.4F)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = mentalTip.imageRes),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_3)))
        Text(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_1))
                .align(Alignment.CenterHorizontally),
            fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PlayfairDisplay,
            textAlign = TextAlign.Center,
            text = stringResource(id = mentalTip.titleRes),
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_3)))
        Text(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_1))
                .align(Alignment.CenterHorizontally),
            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
            textAlign = TextAlign.Center,
            fontFamily = PlayfairDisplay,
            text = stringResource(id = mentalTip.textRes),
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_3)))
    }
}