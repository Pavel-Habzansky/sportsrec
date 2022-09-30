package com.pavelhabzansky.data.features.auth.service

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pavelhabzansky.domain.features.auth.model.User
import com.pavelhabzansky.domain.features.auth.service.AuthService

class AuthServiceImpl(
) : AuthService {

    override fun signIn(
        email: String,
        password: String,
        onComplete: (String?, Throwable?) -> Unit
    ) {
        Firebase.auth
            .signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> onComplete(task.result.user?.email, task.exception) }
    }

    override fun signUp(
        email: String,
        password: String,
        onComplete: (String?, Throwable?) -> Unit
    ) {
        Firebase.auth
            .createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task -> onComplete(task.result.user?.email, task.exception) }
    }
}