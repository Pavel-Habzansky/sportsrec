package com.pavelhabzansky.data.features.auth

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.pavelhabzansky.domain.features.auth.service.AuthService

class AuthServiceImpl : AuthService {

    override fun signIn(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Firebase.auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                task.exception?.let {
                    onError(it)
                    return@addOnCompleteListener
                }

                onSuccess()
            }
    }

    override fun signUp(
        email: String,
        password: String,
        onSuccess: () -> Unit,
        onError: (Throwable) -> Unit
    ) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {task->
                task.exception?.let {
                    onError(it)
                    return@addOnCompleteListener
                }

                onSuccess()
            }
    }
}