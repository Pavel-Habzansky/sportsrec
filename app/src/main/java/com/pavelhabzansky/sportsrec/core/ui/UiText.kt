package com.pavelhabzansky.sportsrec.core.ui

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class DynamicText(val text: String) : UiText()
    data class ResourceText(@StringRes val resId: Int) : UiText()

    fun asText(context: Context): String {
        return when (this) {
            is DynamicText -> text
            is ResourceText -> context.getString(resId)
        }
    }
}