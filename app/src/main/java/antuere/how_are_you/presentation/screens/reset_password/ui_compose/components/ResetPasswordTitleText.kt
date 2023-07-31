package antuere.how_are_you.presentation.screens.reset_password.ui_compose.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.card.GradientCard
import antuere.how_are_you.presentation.base.ui_theme.GradientDefaults

@Composable
fun ResetPasswordTitleText() {
    GradientCard(
        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_small_2)),
        shape = MaterialTheme.shapes.extraLarge,
        elevation = CardDefaults.cardElevation(),
        gradient = GradientDefaults.tertiary()
    ) {
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_4)))
        Image(
            modifier = Modifier
                .fillMaxSize(0.2f)
                .align(Alignment.CenterHorizontally),
            painter = painterResource(id = R.drawable.ic_reset_password),
            contentDescription = "Reset password",
            contentScale = ContentScale.Fit
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(0.95F)
                .padding(dimensionResource(id = R.dimen.padding_normal_0)),
            text = stringResource(id = R.string.reset_password_plug),
            fontWeight = FontWeight.Medium,
            fontSize = dimensionResource(id = R.dimen.textSize_normal_0).value.sp,
            textAlign = TextAlign.Center
        )
    }
}