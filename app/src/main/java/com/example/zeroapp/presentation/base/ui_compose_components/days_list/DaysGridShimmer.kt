package com.example.zeroapp.presentation.base.ui_compose_components.days_list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import com.example.zeroapp.R
import com.example.zeroapp.util.shimmer
import timber.log.Timber


@Composable
fun DaysGridShimmer(
    cellsAmount: Int,
    aspectRatio: Float
) {
    Timber.i("MVI error test : composed in daysGrid shimmer")

    LazyVerticalGrid(
        columns = GridCells.Fixed(cellsAmount),
        modifier = Modifier.fillMaxSize()
    ) {
        items(8) {
            Card(
                modifier = Modifier
                    .padding(dimensionResource(id = R.dimen.padding_small_0))
                    .shimmer(500),
                shape = MaterialTheme.shapes.large,
                colors = CardDefaults.cardColors(
                    containerColor = Color.LightGray.copy(
                        alpha = 0.7F
                    )
                )
            ) {
                Box(
                    modifier = Modifier.aspectRatio(aspectRatio)
                )
            }
        }
    }
}
