package com.pavelhabzansky.sportsrec.core

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import com.pavelhabzansky.sportsrec.R

fun passwordInputVisualTransformation(passwordVisible: Boolean): VisualTransformation {
    return if (passwordVisible) {
        VisualTransformation.None
    } else {
        PasswordVisualTransformation()
    }
}

@Composable
fun passwordFieldIcon(passwordVisible: Boolean): Painter {
    return if (passwordVisible) {
        painterResource(id = R.drawable.password_visible)
    } else {
        painterResource(
            id = R.drawable.password_invisible
        )
    }
}