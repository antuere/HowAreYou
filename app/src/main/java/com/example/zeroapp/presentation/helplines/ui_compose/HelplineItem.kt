package com.example.zeroapp.presentation.helplines.ui_compose

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.domain.dto.helplines.Helpline
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_theme.PlayfairDisplay

@Composable
fun HelplineItem(
    modifier: Modifier = Modifier,
    helpline: Helpline
) {
    Card(
        modifier = modifier,
        shape = MaterialTheme.shapes.large,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Text(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_1))
                .align(Alignment.Start),
            fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp,
            fontWeight = FontWeight.Medium,
            fontFamily = PlayfairDisplay,
            textAlign = TextAlign.Center,
            text = stringResource(id = helpline.nameResId),
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_3)))

        Text(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_1))
                .align(Alignment.CenterHorizontally),
            fontSize = dimensionResource(id = R.dimen.textSize_normal_2).value.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            text = helpline.phone,
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_3)))
    }
}