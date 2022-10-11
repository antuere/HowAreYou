package antuere.domain.usecases

interface UseCase<Type, Param> {
    suspend operator fun invoke(param: Param): Type
}