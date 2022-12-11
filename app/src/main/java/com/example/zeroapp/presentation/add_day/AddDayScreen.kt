package com.example.zeroapp.presentation.add_day

import androidx.compose.foundation.layout.*
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.zeroapp.R
import com.example.zeroapp.presentation.base.ui_compose_components.AppBarState
import com.example.zeroapp.presentation.base.ui_compose_components.text_field.DefaultTextField
import com.example.zeroapp.presentation.base.ui_theme.PlayfairDisplay

@Composable
fun AddDayScreen(
    onComposing: (AppBarState, Boolean) -> Unit,
    onNavigateUp: () -> Unit,
    addDayViewModel: AddDayViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = true) {
        onComposing(
            AppBarState(
                titleId = R.string.today,
                navigationIcon = Icons.Filled.ArrowBack,
                navigationOnClick = { onNavigateUp() }
            ),
            false
        )
    }

    var dayDesc by remember { mutableStateOf("") }
    val smileImages = listOf(
        antuere.data.R.drawable.smile_sad,
        antuere.data.R.drawable.smile_none,
        antuere.data.R.drawable.smile_low,
        antuere.data.R.drawable.smile_happy,
        antuere.data.R.drawable.smile_very_happy
    )

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(0.15F))
        Text(
            text = stringResource(id = R.string.how_are_you_today),
            fontSize = dimensionResource(id = R.dimen.textSize_big_1).value.sp,
            fontFamily = PlayfairDisplay
        )
        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacer_height_10)))

        DefaultTextField(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
                .fillMaxWidth(),
            label = stringResource(id = R.string.desc_you_day),
            value = dayDesc,
            onValueChange = { dayDesc = it },
            maxLength = 100,
        )
        Spacer(modifier = Modifier.weight(0.25F))
        Row(
            modifier = Modifier
                .padding(horizontal = dimensionResource(id = R.dimen.padding_normal_3))
        ) {
            smileImages.forEach { imageRes ->
                IconButton(onClick = {
                    addDayViewModel.onClickSmile(imageRes, dayDesc)
                    dayDesc = ""
                    onNavigateUp()
                }) {
                    Icon(painter = painterResource(id = imageRes), contentDescription = null)
                }

            }
        }
        Spacer(modifier = Modifier.weight(0.25F))

    }

}