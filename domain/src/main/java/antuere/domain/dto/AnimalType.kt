package antuere.domain.dto

sealed class AnimalType(val query: String) {
    data class Cats(val _query: String = "cuteCats") : AnimalType(_query)
}