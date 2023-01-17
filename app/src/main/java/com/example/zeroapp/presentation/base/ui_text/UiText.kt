package com.example.zeroapp.presentation.base.ui_text

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.google.android.gms.common.internal.ServiceSpecificExtraArgs.CastExtraArgs

sealed class UiText {
    data class DefaultString(val value: String) : UiText()
    class StringResource(@StringRes val resId: Int) : UiText()
    class StringResourceWithArg(@StringRes val resId: Int, vararg val args: Any) : UiText()
    class StringResourceConcat(
        @StringRes val resIdFirst: Int,
        val firstArgs: Array<Any>,
        @StringRes val resIdSecond: Int,
        val secondArgs: Array<Any>,
        val divider: String = ""
    ) : UiText()

    @Composable
    fun asString(): String {
        return when (this) {
            is DefaultString -> value
            is StringResource -> stringResource(id = resId)
            is StringResourceWithArg -> stringResource(id = resId, formatArgs = args)
            is StringResourceConcat -> {
                val firstString =
                    stringResource(id = resIdFirst, formatArgs = firstArgs)
                val secondSting =
                    stringResource(id = resIdSecond, formatArgs = secondArgs)
                return "$firstString $divider $secondSting"
            }
        }
    }

    fun asString(context: Context): String {
        return when (this) {
            is DefaultString -> value
            is StringResource -> context.getString(resId)
            is StringResourceWithArg -> context.getString(resId, *args)
            is StringResourceConcat -> {
                val firstString =
                    context.getString(resIdFirst, *firstArgs)
                val secondSting =
                    context.getString(resIdSecond, *secondArgs)
                return "$firstString $divider $secondSting"
            }
        }
    }
}

