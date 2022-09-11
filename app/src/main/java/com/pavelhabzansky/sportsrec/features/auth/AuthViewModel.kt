package com.pavelhabzansky.sportsrec.features.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.pavelhabzansky.domain.features.auth.usecase.SignInUseCase
import com.pavelhabzansky.domain.features.auth.usecase.SignUpUseCase
import com.pavelhabzansky.domain.features.auth.usecase.ValidateAuthInputUseCase
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.core.BaseViewModel
import com.pavelhabzansky.sportsrec.core.navigation.Route
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import com.pavelhabzansky.sportsrec.core.ui.UiText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val signIn: SignInUseCase,
    private val signUp: SignUpUseCase,
    private val validateAuthInput: ValidateAuthInputUseCase
) : BaseViewModel() {

    var loginState by mutableStateOf(LoginState())
        private set

    fun onEmailInput(input: String) {
        loginState = loginState.copy(email = input)
    }

    fun onPasswordInput(input: String) {
        loginState = loginState.copy(password = input)
    }

    fun onEvent(event: AuthEvent) {
        when (event) {
            is AuthEvent.SignInClick -> {
                if (!validateInputs(loginState.email, loginState.password)) {
                    return
                }

                val signInParams = SignInUseCase.Params(
                    email = loginState.email,
                    password = loginState.password,
                    onSuccess = ::onSignInSuccess,
                    onError = ::onSignInError
                )
                signIn(signInParams)
            }
            is AuthEvent.SignUpClick -> {
                if (!validateInputs(loginState.email, loginState.password)) {
                    return
                }

                val signUpParams = SignUpUseCase.Params(
                    email = loginState.email,
                    password = loginState.password,
                    onSuccess = ::onSignInSuccess,
                    onError = ::onSignInError
                )
                signUp(signUpParams)
            }
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
        val params = ValidateAuthInputUseCase.Params(
            email = email,
            password = password
        )
        return when (validateAuthInput(params)) {
            ValidateAuthInputUseCase.Result.EMAIL_AND_PASSWORD_EMPTY -> {
                val message = UiText.ResourceText(
                    R.string.auth_invalid_password_and_email_empty
                )
                sendSnack(message)
                false
            }
            ValidateAuthInputUseCase.Result.EMAIL_EMPTY -> {
                val message = UiText.ResourceText(R.string.auth_invalid_email_empty)
                sendSnack(message)
                false
            }
            ValidateAuthInputUseCase.Result.EMAIL_INVALID -> {
                val message = UiText.ResourceText(R.string.auth_invalid_email)
                sendSnack(message)
                false
            }
            ValidateAuthInputUseCase.Result.PASSWORD_EMPTY -> {
                val message = UiText.ResourceText(R.string.auth_invalid_password_empty)
                sendSnack(message)
                false
            }
            ValidateAuthInputUseCase.Result.PASSWORD_SHORT -> {
                val message = UiText.ResourceText(R.string.auth_invalid_short_password)
                sendSnack(message)
                false
            }
            else -> true
        }
    }

    private fun onSignInSuccess() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(UiEvent.Navigate(Route.RECORD_LIST))
        }
    }

    private fun onSignInError(throwable: Throwable) {
        val message = UiText.DynamicText(throwable.message ?: "")
        sendSnack(message)
    }

    private fun onSignUpSuccess() {
        viewModelScope.launch(Dispatchers.Main) {
            _uiEvent.send(UiEvent.Navigate(Route.RECORD_LIST))
        }
    }

    private fun onSignUpError(throwable: Throwable) {

    }

}