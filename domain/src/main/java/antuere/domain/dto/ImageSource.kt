package antuere.domain.dto

import antuere.domain.util.SecretKeys

sealed class ImageSource(
    val name: String,
    val url1: String,
    val url2: String,
    val url3: String,
    val url4: String,
    val webSite: String,
) {
    object Cataas : ImageSource(
        name = "Cataas",
        url1 = "https://cataas.com/cat/cute",
        url2 = "https://cataas.com/cat?type=md",
        url3 = "https://cataas.com/cat?type=or",
        url4 = "https://cataas.com/cat",
        webSite = "https://cataas.com/#/"
    )

    object LoremFlickr : ImageSource(
        name = "LoremFlickr",
        url1 = "https://loremflickr.com/640/640/cute,kitty/all",
        url2 = "https://loremflickr.com/640/640/funny,kitty/all",
        url3 = "https://loremflickr.com/860/860/pretty,kitty/all",
        url4 = "https://loremflickr.com/640/860/cute,kitty/all",
        webSite = "https://loremflickr.com/"
    )

    object TheCatApi : ImageSource(
        name = "TheCatApi",
        url1 = "https://api.thecatapi.com/v1/images/search?mime_types=jpg,png&format=src&category_ids=1&api_key=${SecretKeys.CAT_API_KEY}",
        url2 = "https://api.thecatapi.com/v1/images/search?mime_types=jpg,png&format=src&category_ids=15&api_key=${SecretKeys.CAT_API_KEY}",
        url3 = "https://api.thecatapi.com/v1/images/search?mime_types=jpg,png&format=src&category_ids=5&api_key=${SecretKeys.CAT_API_KEY}",
        url4 = "https://api.thecatapi.com/v1/images/search?mime_types=jpg,png&format=src&api_key=${SecretKeys.CAT_API_KEY}",
        webSite = "https://thecatapi.com/"
    )
}

//Deprecated source.unsplash, but maybe its will be work in future, then use it

//    object Unsplash : ImageSource(
//        name = "Unsplash",
//        url1 = "https://source.unsplash.com/random/?cutecats",
//        url2 = "https://source.unsplash.com/random/?feline",
//        url3 = "https://source.unsplash.com/random/?cat",
//        url4 = "https://source.unsplash.com/random/?kitty",
//        webSite = "https://unsplash.com/"
//    )

