package antuere.how_are_you.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.ime
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity

@Composable
fun ShowToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    val context = LocalContext.current
    Toast.makeText(context, text, duration).show()
}

fun showToastByContext(
    context: Context,
    text: String? = null,
    @StringRes resId: Int? = null,
    duration: Int = Toast.LENGTH_SHORT
) {
    var toastText = text

    resId?.let {
        toastText = context.resources.getString(it)

    }

    Toast.makeText(context, toastText, duration).show()
}

@Composable
fun isKeyboardVisible(): Boolean {
    return WindowInsets.ime.getBottom(LocalDensity.current) > 0
}


