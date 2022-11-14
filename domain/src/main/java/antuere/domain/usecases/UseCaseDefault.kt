package antuere.domain.usecases

interface UseCaseDefault<Type, Param> {
    suspend operator fun invoke(param: Param): Type
}