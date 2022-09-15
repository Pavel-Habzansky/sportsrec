package com.pavelhabzansky.sportsrec.features.auth

import android.content.res.Configuration
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.pavelhabzansky.sportsrec.R
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
        LoadingDialog()
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

@Composable
fun LoadingDialog(
    cornerRadius: Dp = 16.dp,
    paddingStart: Dp = 56.dp,
    paddingEnd: Dp = 56.dp,
    paddingTop: Dp = 32.dp,
    paddingBottom: Dp = 32.dp,
    progressIndicatorColor: Color = Color(0xFF35898f),
    progressIndicatorSize: Dp = 80.dp
) {
    Dialog(onDismissRequest = { /*TODO*/ }) {
        Surface(
            elevation = 4.dp,
            shape = RoundedCornerShape(cornerRadius)
        ) {
            Column(
                modifier = Modifier
                    .padding(start = paddingStart, end = paddingEnd, top = paddingTop),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                ProgressIndicatorLoading(
                    progressIndicatorSize = progressIndicatorSize,
                    progressIndicatorColor = progressIndicatorColor
                )

                // Gap between progress indicator and text
                Spacer(modifier = Modifier.height(32.dp))

                // Please wait text
                Text(
                    modifier = Modifier
                        .padding(bottom = paddingBottom),
                    text = "Please wait...",
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 16.sp,
                    )
                )
            }
        }
    }
}

@Composable
fun ProgressIndicatorLoading(progressIndicatorSize: Dp, progressIndicatorColor: Color) {

    val infiniteTransition = rememberInfiniteTransition()

    val angle by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 600
            }
        )
    )

    CircularProgressIndicator(
        progress = 1f,
        modifier = Modifier
            .size(progressIndicatorSize)
            .rotate(angle)
            .border(
                12.dp,
                brush = Brush.sweepGradient(
                    listOf(
                        Color.White, // add background color first
                        progressIndicatorColor.copy(alpha = 0.1f),
                        progressIndicatorColor
                    )
                ),
                shape = CircleShape
            ),
        strokeWidth = 1.dp,
        color = Color.White // Set background color
    )
}

@Preview
@Composable
fun AuthScreenPreview() {
    AuthScreen(
        onNavigate = {},
        snackbarHostState = rememberScaffoldState().snackbarHostState
    )
}