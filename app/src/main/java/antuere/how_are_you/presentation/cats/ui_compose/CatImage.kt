package antuere.how_are_you.presentation.cats.ui_compose

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import antuere.how_are_you.R
import antuere.how_are_you.util.extensions.animateMoving
import com.bumptech.glide.request.RequestOptions
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.animation.crossfade.CrossfadePlugin
import com.skydoves.landscapist.components.rememberImageComponent
import com.skydoves.landscapist.glide.GlideImage

@Composable
fun CatImage(
    modifier: Modifier = Modifier,
    url: String,
    contentDescription: String = "Cat",
) {
    GlideImage(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraLarge),
//            .aspectRatio(0.9F),
        imageModel = { url },
        imageOptions = ImageOptions(
            alignment = Alignment.Center,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
        ),
        requestOptions = {
            RequestOptions().skipMemoryCache(true)
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

        }
    )
}