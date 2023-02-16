package antuere.how_are_you.presentation.screens.detail.ui_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.util.extensions.paddingTopBar
import timber.log.Timber

@Composable
fun DetailScreenState(
    isLoading: Boolean,
    daySmileImage: Int,
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
        Timber.i("MVI error test : enter in detail day scope ")
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Spacer(modifier = Modifier.weight(0.05F))
            Image(
                modifier = Modifier
                    .fillMaxSize(0.5F)
                    .weight(0.2F),
                painter = painterResource(id = daySmileImage),
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
}