package com.example.zeroapp.presentation.cats.ui_compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.zeroapp.R
import timber.log.Timber

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DefaultGlideImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String,
) {
    GlideImage(
        modifier = modifier,
        model = url,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        alignment = Alignment.Center,
    ) {
        it.circleCrop()
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .placeholder(R.drawable.cat_placeholder)
            .error(R.drawable.cat_black)
    }
}