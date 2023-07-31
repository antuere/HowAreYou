package antuere.how_are_you.presentation.screens.onboard.ui_compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.screens.onboard.ui_compose.OnboardPage

@Composable
fun OnboardPageItem(
    modifier: Modifier = Modifier,
    onboardPage: OnboardPage,
) {
    Column(
        modifier = modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_6)))
        Image(
            modifier = Modifier
                .fillMaxWidth(0.45F)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = onboardPage.imageRes),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_6)))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.headlineSmall,
            fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.sp,
            textAlign = TextAlign.Center,
            text = onboardPage.title.asString(),
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_6)))
        Text(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_small_2))
                .align(Alignment.CenterHorizontally),
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            text = onboardPage.description.asString(),
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}