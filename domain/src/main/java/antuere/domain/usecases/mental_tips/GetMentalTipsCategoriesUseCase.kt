package antuere.domain.usecases.mental_tips

import antuere.domain.dto.mental_tips.MentalTipsCategory
import antuere.domain.repository.MentalTipsRepository
import antuere.domain.usecases.UseCaseDefault

class GetMentalTipsCategoriesUseCase(private val mentalTipsRepository: MentalTipsRepository) :
    UseCaseDefault<List<MentalTipsCategory>, Unit> {


    override suspend fun invoke(param: Unit): List<MentalTipsCategory> {
        return mentalTipsRepository.getMentalTipsCategories()
    }
}