package com.pavelhabzansky.sportsrec.features.auth

import android.content.res.Configuration
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavelhabzansky.sportsrec.R
import com.pavelhabzansky.sportsrec.core.components.LoadingDialog
import com.pavelhabzansky.sportsrec.core.navigation.UiEvent
import com.pavelhabzansky.sportsrec.core.passwordFieldIcon
import com.pavelhabzansky.sportsrec.core.passwordInputVisualTransformation

@Composable
fun AuthScreen(
    onNavigate: (UiEvent.Navigate) -> Unit,
    snackbarHostState: SnackbarHostState,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val configuration = LocalConfiguration.current
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

    val screenState = viewModel.loginState.screenState
    if (screenState == AuthScreenState.Loading) {
        LoadingDialog(text = stringResource(id = R.string.auth_loading))
    }

    when (configuration.orientation) {
        Configuration.ORIENTATION_PORTRAIT -> AuthScreenPortrait(viewModel)
        Configuration.ORIENTATION_LANDSCAPE -> AuthScreenLandscape(viewModel)
    }
}

@Composable
fun AuthScreenPortrait(
    viewModel: AuthViewModel
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .width(IntrinsicSize.Max),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            HeaderText()
            Spacer(modifier = Modifier.height(32.dp))
            Divider(
                modifier = Modifier
                    .clip(RoundedCornerShape(64.dp)),
                thickness = 4.dp,
                color = MaterialTheme.colors.primarySurface,
            )
            Spacer(modifier = Modifier.height(32.dp))
            InputForm(viewModel = viewModel)
            Spacer(modifier = Modifier.height(16.dp))
            ButtonsRow(
                onButtonClick = viewModel::onEvent,
                enabled = viewModel.loginState.screenState == AuthScreenState.Idle
            )
        }
    }

}

@Composable
fun AuthScreenLandscape(
    viewModel: AuthViewModel
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            InputForm(viewModel = viewModel)

            Spacer(modifier = Modifier.width(32.dp))

            Divider(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(4.dp)
                    .clip(RoundedCornerShape(64.dp)),
                color = MaterialTheme.colors.primarySurface,
            )

            Spacer(modifier = Modifier.width(32.dp))

            HeaderText()
        }

        Spacer(modifier = Modifier.height(32.dp))

        ButtonsRow(
            onButtonClick = viewModel::onEvent,
            enabled = viewModel.loginState.screenState == AuthScreenState.Idle
        )
    }
}

@Composable
fun HeaderText() {
    Text(
        text = stringResource(id = R.string.auth_welcome_text),
        style = MaterialTheme.typography.h3,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}

@Composable
fun InputForm(
    viewModel: AuthViewModel
) {
    val focusManager = LocalFocusManager.current

    Column {
        OutlinedTextField(
            value = viewModel.loginState.email,
            onValueChange = { viewModel.onEmailInput(it) },
            label = { Text(text = stringResource(id = R.string.auth_email_input_hint)) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            )
        )
        Spacer(modifier = Modifier.height(16.dp))

        var passwordVisible by rememberSaveable { mutableStateOf(false) }
        OutlinedTextField(
            value = viewModel.loginState.password,
            onValueChange = { viewModel.onPasswordInput(it) },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            label = { Text(text = stringResource(id = R.string.auth_password_input_hint)) },
            visualTransformation = passwordInputVisualTransformation(passwordVisible),
            trailingIcon = {
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(
                        painter = passwordFieldIcon(passwordVisible),
                        contentDescription = null
                    )
                }
            }
        )
    }
}

@Composable
fun ButtonsRow(
    onButtonClick: (AuthEvent) -> Unit,
    enabled: Boolean = true
) {
    val focusManager = LocalFocusManager.current

    Row {
        Button(
            onClick = {
                focusManager.clearFocus()
                onButtonClick(AuthEvent.SignInClick)
            },
            enabled = enabled
        ) {
            Text(text = stringResource(id = R.string.auth_btn_signin))
        }
        Spacer(modifier = Modifier.width(16.dp))
        Button(
            onClick = {
                focusManager.clearFocus()
                onButtonClick(AuthEvent.SignUpClick)
            },
            enabled = enabled
        ) {
            Text(text = stringResource(id = R.string.auth_btn_signup))
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