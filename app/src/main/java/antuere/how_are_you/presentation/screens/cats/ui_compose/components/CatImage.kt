package antuere.how_are_you.presentation.screens.cats.ui_compose.components

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.progress_indicator.LinearProgressBarOrientation
import antuere.how_are_you.presentation.base.ui_compose_components.progress_indicator.LinearProgressBarWrapper
import antuere.how_are_you.util.extensions.animateRotation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage
import com.skydoves.landscapist.glide.GlideImageState

@Composable
fun CatImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String = "Cat",
    recompositionFlag: Boolean,
    onLongClicked: (Bitmap?) -> Unit,
) {
    var imageAsBitmap: Bitmap? = null
    var selected by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (selected) 0.96f else 1f)

    key(recompositionFlag) {
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LinearProgressBarWrapper(
                scale = { scale },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                LinearProgressBarWrapper(
                    scale = { scale },
                    orientation = LinearProgressBarOrientation.VERTICAL,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F)
                )
                GlideImage(
                    modifier = Modifier
                        .graphicsLayer {
                            scaleY = scale
                            scaleX = scale
                        }
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onLongPress = {
                                    onLongClicked(imageAsBitmap)
                                },
                                onPress = {
                                    selected = true
                                    tryAwaitRelease()
                                    selected = false
                                }
                            )
                        }
                        .clip(MaterialTheme.shapes.extraLarge)
                        .fillMaxSize(),
//                    .aspectRatio(0.85F),
                    imageModel = { url },
                    imageOptions = ImageOptions(
                        contentDescription = contentDescription,
                        contentScale = ContentScale.Crop,
                    ),
                    requestOptions = {
                        RequestOptions()
                            .skipMemoryCache(true)
                            .diskCacheStrategy(DiskCacheStrategy.NONE)
                    },
                    component = rememberImageComponent {
                        +CrossfadePlugin(duration = 300)
                    },
                    loading = {
                        Image(
                            modifier = Modifier
                                .fillMaxSize(0.5F)
                                .animateRotation()
                                .align(Alignment.Center),
                            painter = painterResource(id = R.drawable.cat_placeholder),
                            contentDescription = "Cat loading"
                        )
                    },
                    failure = {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Image(
                                modifier = Modifier
                                    .fillMaxSize(0.7F),
                                painter = painterResource(id = R.drawable.cat_black),
                                contentDescription = "Cat loading error, no internet"
                            )
                            Text(text = stringResource(R.string.cats_no_internet))
                        }

                    },
                    onImageStateChanged = { imageState ->
                        if (imageState is GlideImageState.Success) {
                            imageAsBitmap = imageState.imageBitmap?.asAndroidBitmap()
                        } else if (imageState is GlideImageState.Failure) {
                            imageAsBitmap = null
                        }
                    }
                )
                LinearProgressBarWrapper(
                    scale = { scale },
                    orientation = LinearProgressBarOrientation.VERTICAL,
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1F)
                )
            }
            LinearProgressBarWrapper(
                scale = { scale },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1F)
            )
        }
    }
}