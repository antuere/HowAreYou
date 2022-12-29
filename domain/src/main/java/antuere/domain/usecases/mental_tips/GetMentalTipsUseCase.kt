package antuere.domain.usecases.mental_tips

import antuere.domain.dto.mental_tips.MentalTip
import antuere.domain.dto.mental_tips.TipsCategoryName
import antuere.domain.repository.MentalTipsRepository
import antuere.domain.usecases.UseCaseDefault

class GetMentalTipsUseCase(private val mentalTipsRepository: MentalTipsRepository) :
    UseCaseDefault<List<MentalTip>, TipsCategoryName> {

    override suspend fun invoke(param: TipsCategoryName): List<MentalTip> {
        return mentalTipsRepository.getMentalTips(categoryName = param)
    }
}
