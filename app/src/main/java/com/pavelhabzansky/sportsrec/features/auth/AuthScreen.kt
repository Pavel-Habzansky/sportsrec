package com.pavelhabzansky.sportsrec.features.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent

@Composable
fun AuthScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.Navigate -> onNavigate(event)
                is UiEvent.Snackbar -> snackbarHostState.showSnackbar(
                    message = event.text.asText(context)
                )
                else -> {}
            }
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OutlinedTextField(
            value = viewModel.loginState.email,
            onValueChange = { viewModel.onEmailInput(it) },
            label = { Text(text = stringResource(id = R.string.auth_email_input_hint)) })
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = viewModel.loginState.password,
            onValueChange = { viewModel.onPasswordInput(it) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            label = { Text(text = stringResource(id = R.string.auth_password_input_hint)) },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.onEvent(AuthEvent.SignInClick)
                }
            ) {
                Text(text = stringResource(id = R.string.auth_btn_signin))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    focusManager.clearFocus()
                    viewModel.onEvent(AuthEvent.SignUpClick)
                }
            ) {
                Text(text = stringResource(id = R.string.auth_btn_signup))
            }
        }
    }
}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreen(
        onNavigate = {},
        snackbarHostState = rememberScaffoldState().snackbarHostState
    )
}