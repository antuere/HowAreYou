package antuere.how_are_you.presentation.screens.cats.state

import antuere.domain.dto.ImageSource

data class CatsState(
    val imageSource: ImageSource = ImageSource.Unsplash,
    val allImageSources: List<ImageSource> = listOf(ImageSource.Unsplash, ImageSource.LoremFlickr),
    val isLoading: Boolean = true,
    val forceRecompositionFlag: Boolean = true,
    val isShowSourceSelectionDialog: Boolean = false,
)