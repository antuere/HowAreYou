package antuere.domain.usecases.authentication

import antuere.domain.authentication_manager.ResultListener

interface UseCaseForAuth {
    suspend operator fun invoke(resultListener: ResultListener, vararg fields: String)
}