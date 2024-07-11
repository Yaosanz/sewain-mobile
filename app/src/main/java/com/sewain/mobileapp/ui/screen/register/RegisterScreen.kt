package com.sewain.mobileapp.ui.screen.register

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sewain.mobileapp.R
import com.sewain.mobileapp.di.Injection
import com.sewain.mobileapp.ui.ViewModelFactory
import com.sewain.mobileapp.ui.theme.Gray700
import com.sewain.mobileapp.ui.theme.RoyalBlue
import com.sewain.mobileapp.ui.theme.SewainAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    modifier: Modifier = Modifier,
    viewModel: RegisterViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideUserRepository(LocalContext.current))
    ),
    navigateToLogin: () -> Unit
) {
    var inputUsername by remember { mutableStateOf("") }
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    var inputConfirmPassword by remember { mutableStateOf("") }
    val passwordHidden by rememberSaveable { mutableStateOf(true) }

    val snackbarHost by remember { mutableStateOf((SnackbarHostState())) }
    val scope = rememberCoroutineScope()

    var loading by remember { mutableStateOf(false) }
    var success by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(true) }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHost) },
    ) { padding ->
        RegisterContent(
            modifier = modifier.padding(padding),
            inputUsername = inputUsername,
            onInputUsername = { newInputUsername ->
                inputUsername = newInputUsername
            },
            inputEmail = inputEmail,
            onInputEmail = { newInputEmail ->
                inputEmail = newInputEmail
            },
            inputPassword = inputPassword,
            onInputPassword = { newInputPassword ->
                inputPassword = newInputPassword
            },
            inputConfirmPassword = inputConfirmPassword,
            onInputConfirmPassword = { newInputConfirmPassword ->
                inputConfirmPassword = newInputConfirmPassword
            },
            passwordHidden = passwordHidden,
            navigateToLogin = navigateToLogin,
            onClickRegister = {
                scope.launch {
                    if (inputUsername.isEmpty() ||
                        inputEmail.isEmpty() ||
                        inputPassword.isEmpty() ||
                        inputConfirmPassword.isEmpty()
                    ) {
                        snackbarHost.showSnackbar(
                            message = "Error: Incomplete Registration!",
                            duration = SnackbarDuration.Short
                        )
                    } else if (inputPassword.contentEquals(inputConfirmPassword)) {
                        viewModel.register(inputUsername, inputEmail, inputPassword).let {
                            success = viewModel.signupSuccess.value

                            loading = viewModel.isSignupLoading.value
                            enabled = false

                            delay(2000)
                            snackbarHost.showSnackbar(
                                message = viewModel.signupMessage.value,
                                duration = SnackbarDuration.Short
                            )

                            if (success) {
                                navigateToLogin()
                            }
                        }
                        loading = false
                        enabled = true
                    } else {
                        snackbarHost.showSnackbar(
                            message = "Password Do Not Match!",
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            },
            loading = loading,
            enabled = enabled,
        )
    }
}

@Composable
fun RegisterContent(
    modifier: Modifier,
    inputUsername: String,
    onInputUsername: (String) -> Unit,
    inputEmail: String,
    onInputEmail: (String) -> Unit,
    inputPassword: String,
    onInputPassword: (String) -> Unit,
    inputConfirmPassword: String,
    onInputConfirmPassword: (String) -> Unit,
    passwordHidden: Boolean,
    navigateToLogin: () -> Unit,
    onClickRegister: () -> Unit,
    loading: Boolean,
    enabled: Boolean,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.blob_element),
            contentDescription = null,
            modifier = modifier.size(625.dp),
            alignment = Alignment.TopCenter,
        )

        // Main UI
        Column {
            Box(
                modifier = modifier
                    .padding(top = 50.dp)
                    .size(125.dp)
                    .clip(CircleShape)
                    .align(Alignment.CenterHorizontally)
                    .background(MaterialTheme.colorScheme.onBackground)
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_sewain),
                    contentDescription = null,
                    modifier = modifier
                        .size(100.dp)
                        .align(Alignment.Center),
                )
            }

            Text(
                text = stringResource(R.string.sign_up),
                modifier = modifier
                    .padding(top = 32.dp)
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 48.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = stringResource(R.string.sign_up_message),
                modifier = modifier
                    .align(Alignment.CenterHorizontally),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 21.sp,
                fontWeight = FontWeight.Normal
            )

            OutlinedTextField(
                value = inputUsername,
                onValueChange = onInputUsername,
                modifier = modifier
                    .padding(
                        top = 48.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                    .size(360.dp, 58.dp)
                    .align(Alignment.CenterHorizontally),
                textStyle = TextStyle(
                    color = Gray700,
                    fontSize = 18.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.username),
                        color = Gray700,
                        fontSize = 18.sp,
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Person,
                        contentDescription = null,
                        tint = Gray700,
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = RoyalBlue,
                        backgroundColor = RoyalBlue
                    ),
                    focusedIndicatorColor = RoyalBlue,
                )
            )

            OutlinedTextField(
                value = inputEmail,
                onValueChange = onInputEmail,
                modifier = modifier
                    .padding(
                        top = 13.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                    .size(360.dp, 58.dp)
                    .align(Alignment.CenterHorizontally),
                textStyle = TextStyle(
                    color = Gray700,
                    fontSize = 18.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.email),
                        color = Gray700,
                        fontSize = 18.sp
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = null,
                        tint = Gray700,
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = RoyalBlue,
                        backgroundColor = RoyalBlue
                    ),
                    focusedIndicatorColor = RoyalBlue,
                )
            )

            OutlinedTextField(
                value = inputPassword,
                onValueChange = onInputPassword,
                modifier = modifier
                    .padding(
                        top = 13.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                    .size(360.dp, 58.dp)
                    .align(Alignment.CenterHorizontally),
                textStyle = TextStyle(
                    color = Gray700,
                    fontSize = 18.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.password),
                        color = Gray700,
                        fontSize = 18.sp
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = Gray700
                    )
                },
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = Gray700,
                        backgroundColor = Gray700
                    ),
                )
            )

            OutlinedTextField(
                value = inputConfirmPassword,
                onValueChange = onInputConfirmPassword,
                modifier = modifier
                    .padding(
                        top = 13.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                    .size(360.dp, 58.dp)
                    .align(Alignment.CenterHorizontally),
                textStyle = TextStyle(
                    color = Gray700,
                    fontSize = 18.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.confirm_password),
                        color = Gray700,
                        fontSize = 18.sp
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Lock,
                        contentDescription = null,
                        tint = Gray700
                    )
                },
                visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                singleLine = true,
                shape = RoundedCornerShape(10.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White,
                    disabledContainerColor = Color.White,
                    cursorColor = Color.Black,
                    selectionColors = TextSelectionColors(
                        handleColor = Gray700,
                        backgroundColor = Gray700
                    ),
                )
            )

            Button(
                onClick = onClickRegister,
                modifier
                    .padding(
                        top = 19.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                    .size(360.dp, 58.dp)
                    .align(Alignment.CenterHorizontally),
                enabled = enabled,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.onBackground)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(
                        text = stringResource(R.string.sign_up),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(
                        top = 8.dp
                    ),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.already_account_message),
                    modifier = modifier.padding(start = 16.dp),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )

                TextButton(
                    onClick = { navigateToLogin() },
                    modifier = modifier.padding(end = 8.dp)
                ) {
                    Text(
                        text = stringResource(R.string.sign_in),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }
            }

        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
fun PreviewRegisterScreen() {
    SewainAppTheme {
        RegisterScreen(navigateToLogin = { })
    }
}