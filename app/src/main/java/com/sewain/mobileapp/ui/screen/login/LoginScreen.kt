package com.sewain.mobileapp.ui.screen.login

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Alignment.Companion.TopCenter
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
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.sewain.mobileapp.R
import com.sewain.mobileapp.di.Injection
import com.sewain.mobileapp.ui.ViewModelFactory
import com.sewain.mobileapp.ui.navigation.Screen
import com.sewain.mobileapp.ui.theme.Gray700
import com.sewain.mobileapp.ui.theme.RoyalBlue
import com.sewain.mobileapp.ui.theme.SewainAppTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    modifier: Modifier = Modifier,
    viewModel: LoginViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideUserRepository(LocalContext.current))
    ),
    navController: NavController,
    snackbarHostState: SnackbarHostState,
) {
    var inputEmail by remember { mutableStateOf("") }
    var inputPassword by remember { mutableStateOf("") }
    val passwordHidden by rememberSaveable { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    var loading by remember { mutableStateOf(false) }
    var success by remember { mutableStateOf(false) }
    var enabled by remember { mutableStateOf(true) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
    ) { padding ->
        LoginContent(
            modifier = modifier.padding(padding),
            inputEmail = inputEmail,
            onInputEmail = { newInputEmail ->
                inputEmail = newInputEmail
            },
            inputPassword = inputPassword,
            onInputPassword = { newInputPassword ->
                inputPassword = newInputPassword
            },
            passwordHidden = passwordHidden,
            navigateToRegister = {
                navController.popBackStack()
                navController.navigate(Screen.Register.route)
            },
            onClickLogin = {
                scope.launch {
                    if (inputEmail.isEmpty() || inputPassword.isEmpty()) {
                        snackbarHostState.showSnackbar(
                            message = "Error: Incomplete Registration",
                            duration = SnackbarDuration.Short
                        )
                    } else {
                        viewModel.login(inputEmail, inputPassword).let {
                            success = viewModel.signInSuccess.value

                            loading = viewModel.isSignInLoading.value
                            enabled = false

                            delay(2000)

                            snackbarHostState.showSnackbar(
                                message = viewModel.signInMessage.value,
                                duration = SnackbarDuration.Short
                            )
                        }
                        loading = false
                        enabled = true
                    }
                }
            },
            loading = loading,
            enabled = enabled,
        )
    }
}

@Composable
fun LoginContent(
    modifier: Modifier,
    inputEmail: String,
    onInputEmail: (String) -> Unit,
    inputPassword: String,
    onInputPassword: (String) -> Unit,
    passwordHidden: Boolean,
    navigateToRegister: () -> Unit,
    onClickLogin: () -> Unit,
    loading: Boolean,
    enabled: Boolean,
) {
    Box(modifier = modifier.fillMaxSize()) {
        Image(
            painter = painterResource(R.drawable.blob_element),
            contentDescription = null,
            modifier = modifier.size(625.dp),
            alignment = TopCenter,
        )

        // Main UI
        Column {
            Box(
                modifier = modifier
                    .padding(top = 50.dp)
                    .size(125.dp)
                    .clip(CircleShape)
                    .align(CenterHorizontally)
                    .background(MaterialTheme.colorScheme.primary)
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
                text = stringResource(R.string.sign_in),
                modifier = modifier
                    .padding(top = 32.dp)
                    .align(CenterHorizontally),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 48.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = stringResource(R.string.sign_in_message),
                modifier = modifier
                    .align(CenterHorizontally),
                color = MaterialTheme.colorScheme.primary,
                fontSize = 21.sp,
                fontWeight = FontWeight.Normal
            )

            OutlinedTextField(
                value = inputEmail,
                onValueChange = onInputEmail,
                modifier = modifier
                    .padding(
                        top = 48.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                    .size(360.dp, 58.dp)
                    .align(CenterHorizontally),
                textStyle = TextStyle(
                    color = Gray700,
                    fontSize = 18.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.email),
                        fontSize = 18.sp,
                        color = Gray700
                    )
                },
                trailingIcon = {
                    Icon(
                        Icons.Outlined.Email,
                        contentDescription = null,
                        tint = Gray700
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
                    .align(CenterHorizontally),
                textStyle = TextStyle(
                    color = Gray700,
                    fontSize = 18.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(R.string.password),
                        fontSize = 18.sp,
                        color = Gray700
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
                        handleColor = RoyalBlue,
                        backgroundColor = RoyalBlue
                    ),
                    focusedIndicatorColor = RoyalBlue,
                )
            )

//            TextButton(
//                onClick = { },
//                modifier = modifier
//                    .padding(end = 8.dp)
//                    .align(End)
//            ) {
//                Text(
//                    stringResource(R.string.forgot_password),
//                    color = MaterialTheme.colorScheme.primary
//                )
//            }

            Button(
                onClick = { onClickLogin() },
                modifier
                    .padding(
                        top = 16.dp,
                        start = 8.dp,
                        end = 8.dp
                    )
                    .size(360.dp, 58.dp)
                    .align(CenterHorizontally),
                enabled = enabled,
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                if (loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.primary
                    )
                } else {
                    Text(
                        text = stringResource(R.string.sign_in),
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                HorizontalDivider(
                    modifier = modifier
                        .width(150.dp)
                        .padding(start = 16.dp)
                        .align(CenterVertically),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = stringResource(R.string.or)
                )

                HorizontalDivider(
                    modifier = modifier
                        .width(150.dp)
                        .padding(end = 16.dp)
                        .align(CenterVertically),
                    thickness = 1.dp,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            TextButton(
                onClick = { navigateToRegister() },
                modifier = modifier
                    .padding(top = 16.dp)
                    .align(CenterHorizontally)
            ) {
                Text(
                    stringResource(R.string.create_new_account),
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

        }
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_4_XL,
    uiMode = UI_MODE_NIGHT_YES,
)
@Composable
fun PreviewLoginScreen() {
    SewainAppTheme {
        LoginScreen(
            navController = rememberNavController(),
            snackbarHostState = remember { SnackbarHostState() },
        )
    }
}