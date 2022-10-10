package antuere.domain.usecases

interface UseCase <Type, Param> {
     suspend fun run (param: Param) : Type
     suspend operator fun invoke(param: Param) = run(param)
}