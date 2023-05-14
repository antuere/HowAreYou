package antuere.domain.dto

sealed class ImageSource(
    val name: String,
    val url1: String,
    val url2: String,
    val url3: String,
    val url4: String,
    val webSite: String
) {
    object Unsplash : ImageSource(
        name = "Unsplash",
        url1 = "https://source.unsplash.com/random/?cutecats",
        url2 = "https://source.unsplash.com/random/?feline",
        url3 = "https://source.unsplash.com/random/?cat",
        url4 = "https://source.unsplash.com/random/?kitty",
        webSite = "https://unsplash.com/"
    )

    object LoremFlickr : ImageSource(
        name = "LoremFlickr",
        url1 = "https://loremflickr.com/640/640/cute,kitty/all",
        url2 = "https://loremflickr.com/640/640/funny,kitty/all",
        url3 = "https://loremflickr.com/860/860/pretty,kitty/all",
        url4 = "https://loremflickr.com/640/860/cute,kitty/all",
        webSite = "https://loremflickr.com/"
    )
}
