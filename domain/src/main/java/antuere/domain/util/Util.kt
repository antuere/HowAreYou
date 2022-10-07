package antuere.domain.util

import java.text.SimpleDateFormat
import java.util.*

//Конвертируем календарь в строку нужного формата
fun Calendar.getString(): String {
    val formatter = SimpleDateFormat("dd/MM/yy", Locale.US)

    return formatter.format(this.time)
}
