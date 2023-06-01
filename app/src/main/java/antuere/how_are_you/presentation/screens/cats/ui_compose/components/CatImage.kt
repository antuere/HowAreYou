package antuere.how_are_you.presentation.screens.cats.ui_compose.components

import android.graphics.Bitmap
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import antuere.how_are_you.util.extensions.animateRotation
import antuere.how_are_you.util.extensions.progressBox
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
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
        GlideImage(
            modifier = modifier,
            imageModel = { url },
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
            success = {
                val animatedProgress by animateFloatAsState(
                    targetValue = (1 - scale) * 25,
                    animationSpec = tween(500)
                )
                val bitmap = it.imageBitmap ?: return@GlideImage
                Image(
                    modifier = Modifier
                        .progressBox(
                            progress = animatedProgress,
                            color = MaterialTheme.colorScheme.primary
                        )
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
                        .fillMaxSize()
                        .clip(MaterialTheme.shapes.extraLarge),
                    bitmap = bitmap,
                    contentDescription = contentDescription,
                    contentScale = ContentScale.Crop
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
    }
}