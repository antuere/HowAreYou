package com.example.zeroapp.presentation.base.ui_text

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed class UiText {
    data class DefaultString(val value: String) : UiText()
    class StringResource(@StringRes val resId: Int) : UiText()
    class StringResourceWithArg(@StringRes val resId: Int, vararg val args: Any) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DefaultString -> value
            is StringResource -> stringResource(id = resId)
            is StringResourceWithArg -> stringResource(id = resId, formatArgs = args)
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DefaultString -> value
            is StringResource -> context.getString(resId)
            is StringResourceWithArg -> context.getString(resId, *args)
        }
    }
}

