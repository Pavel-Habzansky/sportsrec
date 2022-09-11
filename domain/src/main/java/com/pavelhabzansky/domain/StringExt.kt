package com.pavelhabzansky.domain

import android.util.Patterns

fun String.matchesEmail(): Boolean {
    return !isBlank() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}