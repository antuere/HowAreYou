package antuere.how_are_you.presentation.screens.detail.ui_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.util.extensions.paddingTopBar

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
            .paddingTopBar()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))
            Image(
                modifier = Modifier.size(100.dp),
                painter = painterResource(id = daySmileImage),
                colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_1)))

            Text(
                text = dateString,
                fontSize = dimensionResource(id = R.dimen.textSize_big_1).value.sp
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_2)))

            Text(
                modifier = Modifier
                    .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_2)),
                text = dayText,
                fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp
            )
        }
    }
}