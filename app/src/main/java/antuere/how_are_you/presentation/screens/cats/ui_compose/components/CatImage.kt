package antuere.how_are_you.presentation.screens.cats.ui_compose.components

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import antuere.how_are_you.R
import antuere.how_are_you.presentation.base.ui_compose_components.progress_indicator.LinearProgressBarOrientation
import antuere.how_are_you.presentation.base.ui_compose_components.progress_indicator.LinearProgressBarWrapper
import antuere.how_are_you.util.extensions.animateMoving
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
    onLongClicked: (Bitmap?) -> Unit,
) {
    var imageAsBitmap: Bitmap? = null
    var selected by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(if (selected) 0.96f else 1f)

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LinearProgressBarWrapper(
            scale = { scale },
            modifier = Modifier.padding(start = 4.dp, end = 2.dp)
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            LinearProgressBarWrapper(
                scale = { scale },
                orientation = LinearProgressBarOrientation.VERTICAL,
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
                    .clip(MaterialTheme.shapes.extraLarge),
//                    .aspectRatio(0.85F),
                imageModel = { url },
                imageOptions = ImageOptions(
                    alignment = Alignment.Center,
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
                            .animateMoving()
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
                    }
                }
            )

            LinearProgressBarWrapper(
                scale = { scale },
                orientation = LinearProgressBarOrientation.VERTICAL,
            )
        }
        LinearProgressBarWrapper(
            scale = { scale },
            modifier = Modifier.padding(start = 4.dp, end = 2.dp)
        )
    }
}