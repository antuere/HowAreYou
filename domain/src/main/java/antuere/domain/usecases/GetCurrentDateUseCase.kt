package antuere.domain.usecases

import java.text.SimpleDateFormat
import java.util.*

class GetCurrentDateUseCase {
    operator fun invoke(): String {
        val simpleDateFormat = SimpleDateFormat("dd/MM/yy", Locale.US)
        return simpleDateFormat.format(Date())
    }
}