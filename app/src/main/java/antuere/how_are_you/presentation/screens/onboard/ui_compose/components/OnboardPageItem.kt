package antuere.how_are_you.presentation.screens.onboard.ui_compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import antuere.how_are_you.util.extensions.fixedSize

@Composable
fun OnboardPageItem(
    modifier: Modifier = Modifier,
    onboardPage: OnboardPage,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_6)))
        Image(
            modifier = Modifier
                .fillMaxWidth(0.55F)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = onboardPage.imageRes),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_6)))
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = dimensionResource(id = R.dimen.textSize_big_0).value.fixedSize,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            text = onboardPage.title.asString(),
        )

        Spacer(modifier = Modifier.weight(1f))
        Text(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_0))
                .align(Alignment.CenterHorizontally),
            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
            fontWeight = FontWeight.Normal,
            textAlign = TextAlign.Center,
            text = onboardPage.description.asString(),
        )
        Spacer(modifier = Modifier.weight(1f))
    }
}