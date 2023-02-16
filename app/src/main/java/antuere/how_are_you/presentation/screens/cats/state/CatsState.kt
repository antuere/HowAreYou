package antuere.how_are_you.presentation.screens.cats.state

data class CatsState(
    var urlList: List<String> = listOf(
        "https://source.unsplash.com/random/?cutecats",
        "https://source.unsplash.com/random/?feline",
        "https://source.unsplash.com/random/?cat",
        "https://source.unsplash.com/random/?kitty"
    )
)