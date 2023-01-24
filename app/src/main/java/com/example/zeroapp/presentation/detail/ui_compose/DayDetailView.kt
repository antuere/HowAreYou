package com.example.zeroapp.presentation.detail.ui_compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.zeroapp.R
import com.example.zeroapp.util.paddingTopBar

@Composable
fun DayDetailView(
    @DrawableRes imageResId: Int,
    dateString: String,
    dayText: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .paddingTopBar(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(0.05F))

        Image(
            modifier = Modifier
                .fillMaxSize(0.5F)
                .weight(0.2F),
            painter = painterResource(id = imageResId),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

        Text(
            text = dateString,
            fontSize = dimensionResource(id = R.dimen.textSize_big_1).value.sp
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

        Text(
            modifier = Modifier
                .weight(0.7F)
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2)),
            text = dayText,
            fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp
        )
    }
}